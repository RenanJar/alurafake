package br.com.alura.AluraFake.domain.task.validator;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.error.exception.TaskValidationException;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.task.rules.TaskTypeAllowed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskValidatorTest {

    private TaskRule rule1;
    private TaskRule rule2;
    private TaskValidator validator;

    @BeforeEach
    void setup() {

        rule1 = mock(TaskRule.class);
        rule2 = mock(TaskRule.class);


        when(rule1.getClass().getAnnotation(TaskTypeAllowed.class)).thenReturn(new TaskTypeAllowed() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return TaskTypeAllowed.class;
            }

            @Override
            public Type[] value() {
                return new Type[]{Type.SINGLE_CHOICE};
            }
        });
        when(rule2.getClass().getAnnotation(TaskTypeAllowed.class)).thenReturn(null);

        validator = new TaskValidator(List.of(rule1, rule2));
    }

    @Test
    void shouldValidateWithoutErrors() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);

        when(rule1.validate(task)).thenReturn(List.of());
        when(rule2.validate(task)).thenReturn(List.of());

        List<ValidationError> errors = validator.validate(task);

        assertTrue(errors.isEmpty());
        verify(rule1).validate(task);
        verify(rule2).validate(task);
    }

    @Test
    void shouldThrowExceptionWhenRuleReturnsError() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);

        ValidationError error = new ValidationError("field", "CODE", "Message");
        when(rule1.validate(task)).thenReturn(List.of(error));
        when(rule2.validate(task)).thenReturn(List.of());

        TaskValidationException exception = assertThrows(TaskValidationException.class, () -> validator.validate(task));

        assertEquals(1, exception.getErrors().size());
        assertEquals("CODE", exception.getErrors().get(0).getCode());
        verify(rule1).validate(task);
        verify(rule2).validate(task);
    }

    @Test
    void shouldSkipRulesForOtherTypes() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);


        when(rule1.validate(task)).thenReturn(List.of());
        when(rule2.validate(task)).thenReturn(List.of());

        List<ValidationError> errors = validator.validate(task);

        assertTrue(errors.isEmpty());
        verify(rule1, never()).validate(task);
        verify(rule2).validate(task);
    }
}
