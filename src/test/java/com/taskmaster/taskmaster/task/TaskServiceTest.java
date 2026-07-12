package com.taskmaster.taskmaster.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private final TaskMapper taskMapper = new TaskMapper();

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, taskMapper);
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Write Terraform");
        sampleTask.setDescription("Provision ECS + ECR");
        sampleTask.setStatus(TaskStatus.PENDING);
    }

    @Test
    void getAllTasks_returnsMappedList() {
        when(taskRepository.findAll()).thenReturn(List.of(sampleTask));

        List<TaskResponseDto> result = taskService.getAllTasks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Write Terraform");
    }

    @Test
    void getTaskById_whenFound_returnsDto() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        TaskResponseDto result = taskService.getTaskById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getTaskById_whenNotFound_throwsException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(99L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createTask_savesAndReturnsDto() {
        TaskRequestDto request = new TaskRequestDto();
        request.setTitle("Deploy to ECS");
        request.setDescription("Push image and update service");

        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        TaskResponseDto result = taskService.createTask(request);

        assertThat(result.getTitle()).isEqualTo(sampleTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask_whenFound_deletesIt() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(sampleTask);
    }
}
