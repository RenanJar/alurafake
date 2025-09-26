package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskOrderValidationTaskRuleTest {

    private TaskService taskService;
    private TaskOrderValidationTaskRule rule;

    @BeforeEach
    void setup() {
        taskService = Mockito.mock(TaskService.class);
        rule = new TaskOrderValidationTaskRule(taskService);
    }

    @Test
    void shouldReturnNoErrorWhenNoExistingTasks() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(1);
        task.setOrder(1);

        when(taskService.findByCourseIdOrderByOrderAsc(1)).thenReturn(List.of());

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
        verify(taskService).findByCourseIdOrderByOrderAsc(1);
    }

    @Test
    void shouldReturnNoErrorWhenOrderIsNextInSequence() {
        TaskDTO existingTask = new TaskDTO();
        existingTask.setOrder(2);

        TaskDTO task = new TaskDTO();
        task.setCourseId(1);
        task.setOrder(3);

        when(taskService.findByCourseIdOrderByOrderAsc(1)).thenReturn(List.of(existingTask));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
        verify(taskService).findByCourseIdOrderByOrderAsc(1);
    }

    @Test
    void shouldReturnErrorWhenOrderIsSkipped() {
        TaskDTO existingTask = new TaskDTO();
        existingTask.setOrder(2);

        TaskDTO task = new TaskDTO();
        task.setCourseId(1);
        task.setOrder(5);

        when(taskService.findByCourseIdOrderByOrderAsc(1)).thenReturn(List.of(existingTask));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("INVALID_TASK_ORDER", errors.get(0).getCode());
        verify(taskService).findByCourseIdOrderByOrderAsc(1);
    }
}
