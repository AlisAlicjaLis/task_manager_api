package model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    private long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
}
