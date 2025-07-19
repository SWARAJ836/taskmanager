package com.swaraj.taskmanager.service;

import com.swaraj.taskmanager.dto.TaskRequestDto;
import com.swaraj.taskmanager.dto.TaskResponseDto;
import com.swaraj.taskmanager.entity.Task;
import com.swaraj.taskmanager.exception.TaskNotFoundException;
import com.swaraj.taskmanager.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    public TaskResponseDto createTask (TaskRequestDto requestDto){
        Task task = mapToEntity(requestDto);
        Task savedTask = taskRepo.save(task);
        return mapToDto(savedTask);
    }

    // For Mapping Incoming DTOs to Entities for saving in DB
    private Task mapToEntity(TaskRequestDto dto) {
        Task task=new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        return task;
    }

    // For Mapping Outgoing Entities to DTOs for sending Response
    private TaskResponseDto mapToDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );
    }

    public List<TaskResponseDto> getAllTasks() {
        return taskRepo.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = findTask(id);
        return mapToDto(task);
    }

    private Task findTask(Long id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    public TaskResponseDto updateTask(Long id, @Valid TaskRequestDto requestDto) {
        Task task=findTask(id);
        task.setTitle(requestDto.getTitle());
        task.setDescription(requestDto.getDescription());
        task.setStatus(requestDto.getStatus());
        task.setPriority(requestDto.getPriority());
        task.setDueDate(requestDto.getDueDate());
        Task updatedTask = taskRepo.save(task);
        return mapToDto(updatedTask);
    }

    public void deleteTask(Long id) {
        Task task=findTask(id);
        taskRepo.delete(task);
    }
}















