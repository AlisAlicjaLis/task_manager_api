package org.example.task_manager_api.service;

import org.example.task_manager_api.dto.*;
import org.example.task_manager_api.exception.TaskNotFoundException;
import org.example.task_manager_api.model.Status;
import org.example.task_manager_api.model.Task;
import org.example.task_manager_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AtomicLong counter = new AtomicLong();
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setId(counter.incrementAndGet());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        LocalDateTime now = LocalDateTime.now();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);

        taskRepository.save(task);
        return taskMapper.toResponse(task);
    }

    public List<TaskResponse> getAllTasks(Status status, String query) {
        return taskRepository.findAll().stream()
                .filter(task -> status == null || task.getStatus() == status)
                .filter(task -> query == null || query.isBlank() || matches(task, query.toLowerCase(Locale.ROOT)))
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.toResponse(task);
    }

    public TaskResponse updateTaskById(long id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setUpdatedAt(LocalDateTime.now());
        return taskMapper.toResponse(task);
    }

    public void deleteTaskById(long id) {
        if (taskRepository.findById(id).isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    public TaskResponse patchTaskById(long id, PatchTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if (request.getTitle().isPresent()) {
            task.setTitle(request.getTitle().get());
        }
        if (request.getDescription().isPresent()) {
            String desc = request.getDescription().get();
            if (desc != null && desc.length() > 500) {
                throw new IllegalArgumentException("Description too long");
            }
            task.setDescription(desc);
        }
        if (request.getStatus().isPresent()) {
            task.setStatus(request.getStatus().get());
        }
        task.setUpdatedAt(LocalDateTime.now());

        return taskMapper.toResponse(task);
    }

    private boolean matches(Task task, String query) {
        return contains(task.getTitle(), query)
                || contains(task.getDescription(), query);
    }

    private boolean contains(String value, String query) {
        return value != null &&
                value.toLowerCase(Locale.ROOT).contains(query);
    }

    public Map<Status, Long> getStats() {
        Map<Status, Long> result = new EnumMap<>(Status.class);
        for (Status s : Status.values()) result.put(s, 0L);
        taskRepository.findAll().forEach(t -> result.merge(t.getStatus(), 1L, Long::sum));
        return result;
    }
}
