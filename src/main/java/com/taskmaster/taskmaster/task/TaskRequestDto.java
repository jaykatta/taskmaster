package com.taskmaster.taskmaster.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for incoming create/update requests.
 * Keeps the public API contract decoupled from the JPA entity.
 */
@Getter
@Setter
public class TaskRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be under 255 characters")
    private String title;

    @Size(max = 1000, message = "Description must be under 1000 characters")
    private String description;

    private TaskStatus status;
}
