package com.taskmaster.taskmaster.task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = findTaskOrThrow(id);
        return taskMapper.toResponseDto(task);
    }

    public TaskResponseDto createTask(TaskRequestDto requestDto) {
        Task task = taskMapper.toEntity(requestDto);
        Task saved = taskRepository.save(task);
        return taskMapper.toResponseDto(saved);
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto requestDto) {
        Task task = findTaskOrThrow(id);
        taskMapper.applyToEntity(requestDto, task);
        Task saved = taskRepository.save(task);
        return taskMapper.toResponseDto(saved);
    }

    public void deleteTask(Long id) {
        Task task = findTaskOrThrow(id);
        taskRepository.delete(task);
    }

    public List<TaskResponseDto> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
