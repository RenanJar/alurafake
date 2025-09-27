package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.task.entity.Task;
import br.com.alura.AluraFake.domain.task.util.TaskMapper;
import br.com.alura.AluraFake.domain.task.validator.TaskValidator;
import br.com.alura.AluraFake.infra.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskAnswerService taskAnswerService;
    private TaskRepository taskRepository;
    private TaskValidator taskValidator;
    private TaskMapper taskMapper;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskAnswerService = mock(TaskAnswerService.class);
        taskRepository = mock(TaskRepository.class);
        taskValidator = mock(TaskValidator.class);
        taskMapper = mock(TaskMapper.class);
        taskService = new TaskService(taskAnswerService, taskRepository, taskValidator, taskMapper);
    }

    @Test
    void shouldCreateNewTaskAndSaveOptions() {
        TaskDTO request = new TaskDTO();
        request.setType(Type.SINGLE_CHOICE);
        request.setCourseId(1);
        request.setOrder(1);
        request.setOptions(List.of(mock(TaskAnswerDTO.class), mock(TaskAnswerDTO.class)));

        Task savedTask = new Task();
        savedTask.setId(100);

        when(taskMapper.toEntity(request)).thenReturn(savedTask);
        when(taskAnswerService.saveList(request.getOptions(), savedTask.getId())).thenReturn(List.of());

        Integer taskId = taskService.createNewTask(request);

        assertEquals(100, taskId);
        verify(taskValidator).validate(request);
        verify(taskRepository).updateOrder(1, 1);
        verify(taskRepository).save(savedTask);
        verify(taskAnswerService).saveList(request.getOptions(), 100);
    }

    @Test
    void shouldReturnEmptyListForNonChoiceTaskType() {
        TaskDTO request = new TaskDTO();
        request.setType(Type.OPEN_TEXT);
        request.setCourseId(1);
        request.setOrder(1);

        Task savedTask = new Task();
        savedTask.setId(101);

        when(taskMapper.toEntity(request)).thenReturn(savedTask);

        Integer taskId = taskService.createNewTask(request);

        assertEquals(101, taskId);
        verify(taskAnswerService, never()).saveList(any(), anyInt());
    }

    @Test
    void shouldMapTasksFromRepositoryByCourseIdOrderAsc() {
        Task task = new Task();
        TaskDTO dto = new TaskDTO();
        when(taskRepository.findByCourseIdOrderByOrderAsc(1)).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(dto);

        List<TaskDTO> result = taskService.findByCourseIdOrderByOrderAsc(1);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldMapTasksFromRepositoryByCourseId() {
        Task task = new Task();
        TaskDTO dto = new TaskDTO();
        when(taskRepository.findByCourseId(1)).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(dto);

        List<TaskDTO> result = taskService.findByCourseId(1);

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldReturnFalseIfNotAllTaskTypesPresent() {

        when(taskRepository.hasAllTaskTypes(1L,3)).thenReturn(false);

        boolean result = taskService.hasAllTaskTypes(1L);

        assertFalse(result);
    }

    @Test
    void shouldReturnTaskOrderContinuousOrFalse() {
        when(taskRepository.isTaskOrderContinuous(1L)).thenReturn(true);

        Boolean result = taskService.isTaskOrderContinuous(1L);

        assertTrue(result);

        when(taskRepository.isTaskOrderContinuous(2L)).thenReturn(null);
        assertFalse(taskService.isTaskOrderContinuous(2L));
    }
}