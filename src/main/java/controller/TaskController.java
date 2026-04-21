package controller;

import dto.CreateTaskRequest;
import dto.PatchTaskRequest;
import dto.UpdateTaskRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import model.Status;
import model.Task;
import org.springframework.web.bind.annotation.*;
import service.TaskService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public Task createTask(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @GetMapping
    public List<Task> getTasks(@RequestParam Status status) {
        return taskService.getAllTasks(status);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {
        return taskService.updateTaskById(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
    }

    @PatchMapping("/{id}")
    public Task patchTask(@PathVariable Long id, @Valid @RequestBody PatchTaskRequest request) {
        return taskService.patchTaskById(id, request);
    }

    @GetMapping("/search")
    public List<Task> searchTasks(@RequestParam String query) {
        return taskService.searchTasks(query);
    }

    @GetMapping("/stats")
    public Map<Status, Long> stats() {
        return taskService.stats();
    }
}
