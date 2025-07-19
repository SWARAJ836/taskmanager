package com.swaraj.taskmanager.service;

import com.swaraj.taskmanager.dto.TaskRequestDto;
import com.swaraj.taskmanager.dto.TaskResponseDto;
import com.swaraj.taskmanager.entity.Task;
import com.swaraj.taskmanager.enums.Priority;
import com.swaraj.taskmanager.enums.TaskStatus;
import com.swaraj.taskmanager.exception.TaskNotFoundException;
import com.swaraj.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        task = new Task(1L, "Test Title", "Test Description", TaskStatus.PENDING, Priority.MEDIUM, LocalDateTime.now());
    }

    @Test
    void testCreateTask() {
        TaskRequestDto request = new TaskRequestDto("Test Title", "Test Description", TaskStatus.PENDING, Priority.MEDIUM, LocalDateTime.now());
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDto result = taskService.createTask(request);

        assertEquals("Test Title", result.getTitle());
        assertEquals(TaskStatus.PENDING, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(
                Optional.of(task));

        TaskResponseDto result = taskService.getTaskById(1L);

        assertEquals("Test Title", result.getTitle());
        assertEquals("Test Description", result.getDescription());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        List<TaskResponseDto> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
    }

    @Test
    void testUpdateTask() {
        TaskRequestDto request = new TaskRequestDto("Updated Title", "Updated Description", TaskStatus.IN_PROGRESS, Priority.HIGH, LocalDateTime.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDto result = taskService.updateTask(1L, request);

        assertEquals("Updated Title", result.getTitle());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(2L));
    }

}
