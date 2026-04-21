package service;

import dto.CreateTaskRequest;
import dto.PatchTaskRequest;
import dto.UpdateTaskRequest;
import exception.TaskNotFoundException;
import model.Status;
import model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public Task createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setId(counter.incrementAndGet());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setCreatedAt(LocalDateTime.now());

        tasks.put(task.getId(), task);
        return task;
    }

    public List<Task> getAllTasks(Status status) {
        return tasks.values().stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    public Task getTaskById(long id) {
        return Optional.ofNullable(tasks.get(id))
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTaskById(long id, UpdateTaskRequest request) {
        Task task = getTaskById(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        return task;
    }

    public void deleteTaskById(long id) {
        Task task = getTaskById(id);
        tasks.remove(id);
    }

    public Task patchTaskById(long id, PatchTaskRequest request) {
        Task task = getTaskById(id);
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        return task;
    }

    public List<Task> searchTasks(String query) {
        return tasks.values().stream()
                .filter(task -> task.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public Map<Status, Long> stats() {
        return tasks.values().stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }
}
