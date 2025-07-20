package com.swaraj.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swaraj.taskmanager.dto.TaskRequestDto;
import com.swaraj.taskmanager.dto.TaskResponseDto;
import com.swaraj.taskmanager.enums.Priority;
import com.swaraj.taskmanager.enums.TaskStatus;
import com.swaraj.taskmanager.exception.TaskNotFoundException;
import com.swaraj.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;


@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTask_Success() throws Exception {
        TaskRequestDto requestDto = new TaskRequestDto("Title", "Description", TaskStatus.PENDING, Priority.MEDIUM, LocalDateTime.now());
        TaskResponseDto responseDto = new TaskResponseDto(1L, "Title", "Description", TaskStatus.PENDING, Priority.MEDIUM, LocalDateTime.now());

        Mockito.when(taskService.createTask(any(TaskRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetTaskById_Success() throws Exception {
        TaskResponseDto responseDto = new TaskResponseDto(1L, "Title", "Description", TaskStatus.PENDING, Priority.MEDIUM, LocalDateTime.now());
        Mockito.when(taskService.getTaskById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAllTasks_Success() throws Exception {
        List<TaskResponseDto> tasks = Arrays.asList(
                new TaskResponseDto(1L, "Title1", "Description1", TaskStatus.PENDING, Priority.MEDIUM, LocalDateTime.now()),
                new TaskResponseDto(2L, "Title2", "Description2", TaskStatus.COMPLETED, Priority.HIGH, LocalDateTime.now())
        );
        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testValidationError_BadRequest() throws Exception {
        TaskRequestDto invalidRequest = new TaskRequestDto();

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTaskNotFound_Returns404() throws Exception {
        Mockito.when(taskService.getTaskById(99L)).thenThrow(new TaskNotFoundException("Task not found with id: 99"));

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateTask_Success() throws Exception {
        TaskRequestDto updateRequest = new TaskRequestDto("Updated Title", "Updated Description", TaskStatus.IN_PROGRESS, Priority.HIGH, LocalDateTime.now());
        TaskResponseDto updatedResponse = new TaskResponseDto(1L, "Updated Title", "Updated Description", TaskStatus.IN_PROGRESS, Priority.HIGH, LocalDateTime.now());

        Mockito.when(taskService.updateTask(Mockito.eq(1L), any(TaskRequestDto.class))).thenReturn(updatedResponse);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void testDeleteTask_Success() throws Exception {
        Mockito.doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTask_NotFound() throws Exception {
        Mockito.doThrow(new TaskNotFoundException("Task not found with id: 999")).when(taskService).deleteTask(999L);

        mockMvc.perform(delete("/api/tasks/999"))
                .andExpect(status().isNotFound());
    }
}
