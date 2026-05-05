package org.example.task_manager_api.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(long id) {
        super("Task with id " + id + " not found");
    }
}
