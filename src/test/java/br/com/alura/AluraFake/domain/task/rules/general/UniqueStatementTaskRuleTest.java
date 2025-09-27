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

class UniqueStatementTaskRuleTest {

    private TaskService taskService;
    private UniqueStatementTaskRule rule;

    @BeforeEach
    void setup() {
        taskService = Mockito.mock(TaskService.class);
        rule = new UniqueStatementTaskRule(taskService);
    }

    @Test
    void shouldReturnNoErrorWhenNoExistingTasks() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(1L);
        task.setStatement("New Task");

        when(taskService.findByCourseId(1L)).thenReturn(List.of());

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
        verify(taskService).findByCourseId(1L);
    }

    @Test
    void shouldReturnNoErrorWhenStatementIsUnique() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(1L);
        task.setStatement("Unique Task");

        TaskDTO existingTask = new TaskDTO();
        existingTask.setStatement("Different Task");

        when(taskService.findByCourseId(1L)).thenReturn(List.of(existingTask));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
        verify(taskService).findByCourseId(1L);
    }

    @Test
    void shouldReturnErrorWhenStatementAlreadyExists() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(1L);
        task.setStatement("Duplicate Task");

        TaskDTO existingTask = new TaskDTO();
        existingTask.setStatement("  Duplicate   Task ");

        when(taskService.findByCourseId(1L)).thenReturn(List.of(existingTask));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("DUPLICATE_STATEMENT", errors.get(0).getCode());
        verify(taskService).findByCourseId(1L);
    }
}
