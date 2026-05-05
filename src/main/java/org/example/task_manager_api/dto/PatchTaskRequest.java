package org.example.task_manager_api.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.task_manager_api.model.Status;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class PatchTaskRequest {

    private JsonNullable<String> title = JsonNullable.undefined();

    private JsonNullable<String> description = JsonNullable.undefined();

    private JsonNullable<Status> status = JsonNullable.undefined();
}
