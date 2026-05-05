package org.example.task_manager_api.dto;

import org.example.task_manager_api.model.Status;
import java.time.LocalDateTime;

public record TaskResponse(
        long id,
        String title,
        String description,
        Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
