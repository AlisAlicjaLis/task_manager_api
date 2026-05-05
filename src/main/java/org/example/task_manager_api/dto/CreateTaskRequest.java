package org.example.task_manager_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.task_manager_api.model.Status;

@Getter
@Setter
public class CreateTaskRequest {

    @NotBlank
    private String title;

    @Size(max = 500)
    private String description;

    @NotNull
    private Status status;
}
