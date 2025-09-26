package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TaskOrderContinuousRuleTest {

    private TaskService taskService;
    private TaskOrderContinuousRule rule;

    @BeforeEach
    void setup() {
        taskService = mock(TaskService.class);
        rule = new TaskOrderContinuousRule(taskService);
    }

    @Test
    void shouldReturnErrorWhenTaskOrderIsNotContinuous() {
        Long courseId = 1L;
        when(taskService.isTaskOrderContinuous(courseId)).thenReturn(false);

        List<ValidationError> errors = rule.validate(courseId);

        assertEquals(1, errors.size());
        assertEquals("TASK_ORDER_INVALID", errors.get(0).getCode());
        assertEquals("Task orders must be continuous starting from 1", errors.get(0).getMessage());
        verify(taskService).isTaskOrderContinuous(courseId);
    }

    @Test
    void shouldReturnNoErrorWhenTaskOrderIsContinuous() {
        Long courseId = 2L;
        when(taskService.isTaskOrderContinuous(courseId)).thenReturn(true);

        List<ValidationError> errors = rule.validate(courseId);

        assertTrue(errors.isEmpty());
        verify(taskService).isTaskOrderContinuous(courseId);
    }
}
