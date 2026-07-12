package com.taskmaster.taskmaster.task;

import org.springframework.stereotype.Component;

/**
 * Hand-written mapper between the Task entity and its DTOs.
 * (Could be swapped for MapStruct later without changing callers.)
 */
@Component
public class TaskMapper {

    public TaskResponseDto toResponseDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    public Task toEntity(TaskRequestDto dto) {
        Task task = new Task();
        applyToEntity(dto, task);
        return task;
    }

    public void applyToEntity(TaskRequestDto dto, Task task) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
    }
}
