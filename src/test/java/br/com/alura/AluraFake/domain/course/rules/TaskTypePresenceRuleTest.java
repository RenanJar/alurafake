package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TaskTypePresenceRuleTest {

    private TaskService taskService;
    private TaskTypePresenceRule rule;

    @BeforeEach
    void setup() {
        taskService = mock(TaskService.class);
        rule = new TaskTypePresenceRule(taskService);
    }

    @Test
    void shouldReturnErrorWhenNotAllTaskTypesArePresent() {
        Long courseId = 1L;

        when(taskService.hasAllTaskTypes(courseId)).thenReturn(true);

        List<ValidationError> errors = rule.validate(courseId);

        assertEquals(1, errors.size());
        assertEquals("MISSING_TASK_TYPE", errors.get(0).getCode());
        assertEquals("Course must have at least one task of each type", errors.get(0).getMessage());
        verify(taskService).hasAllTaskTypes(courseId);
    }

    @Test
    void shouldReturnNoErrorWhenAllTaskTypesArePresent() {
        Long courseId = 2L;

        when(taskService.hasAllTaskTypes(courseId)).thenReturn(false);

        List<ValidationError> errors = rule.validate(courseId);

        assertTrue(errors.isEmpty());
        verify(taskService).hasAllTaskTypes(courseId);
    }
}
