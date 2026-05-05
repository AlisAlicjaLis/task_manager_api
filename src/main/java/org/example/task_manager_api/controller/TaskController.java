package org.example.task_manager_api.controller;

import org.example.task_manager_api.dto.CreateTaskRequest;
import org.example.task_manager_api.dto.PatchTaskRequest;
import org.example.task_manager_api.dto.TaskResponse;
import org.example.task_manager_api.dto.UpdateTaskRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.task_manager_api.model.Status;
import org.springframework.web.bind.annotation.*;
import org.example.task_manager_api.service.TaskService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    public List<TaskResponse> getTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String query) {
        return taskService.getAllTasks(status, query);
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {
        return taskService.updateTaskById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
    }

    @PatchMapping("/{id}")
    public TaskResponse patchTask(@PathVariable Long id, @Valid @RequestBody PatchTaskRequest request) {
        return taskService.patchTaskById(id, request);
    }

    @GetMapping("/stats")
    public Map<Status, Long> getStats() {
        return taskService.getStats();
    }
}
