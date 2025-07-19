package com.swaraj.taskmanager.controller;

import com.swaraj.taskmanager.dto.TaskRequestDto;
import com.swaraj.taskmanager.dto.TaskResponseDto;
import com.swaraj.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // To Create new Tasks
    @PostMapping("/tasks")
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto requestDto){
        TaskResponseDto created = taskService.createTask(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // To Get All the Tasks
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // To Find a Task by Id
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // To Update a Task by id
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDto requestDto) {
        return ResponseEntity.ok(taskService.updateTask(id, requestDto));
    }

    //To Delete a Task
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}













