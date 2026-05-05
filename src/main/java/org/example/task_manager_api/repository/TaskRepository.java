package org.example.task_manager_api.repository;

import org.example.task_manager_api.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(long id);
    void deleteById(long id);
}
