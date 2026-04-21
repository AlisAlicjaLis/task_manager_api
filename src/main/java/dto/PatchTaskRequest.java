package dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import model.Status;

@Getter
@Setter
public class PatchTaskRequest {

    private String title;

    @Size(max = 500)
    private String description;

    private Status status;
}
