package br.com.alura.AluraFake.domain.course.validator;

import br.com.alura.AluraFake.domain.course.rules.CourseRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.error.exception.TaskValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseValidatorTest {
    private CourseRule rule1;
    private CourseRule rule2;
    private CourseValidator validator;

    @BeforeEach
    void setUp() {
        rule1 = mock(CourseRule.class);
        rule2 = mock(CourseRule.class);
        validator = new CourseValidator(List.of(rule1, rule2));
    }

    @Test
    void shouldReturnEmptyListWhenAllRulesPass() {

        Long courseId = 1L;
        when(rule1.validate(courseId)).thenReturn(List.of());
        when(rule2.validate(courseId)).thenReturn(List.of());


        List<ValidationError> result = validator.validate(courseId);


        assertTrue(result.isEmpty());
        verify(rule1, times(1)).validate(courseId);
        verify(rule2, times(1)).validate(courseId);
    }

    @Test
    void shouldThrowTaskValidationExceptionWhenAnyRuleFails() {
        Long courseId = 2L;
        ValidationError error = new ValidationError("field", "CODE", "Some error");
        when(rule1.validate(courseId)).thenReturn(List.of(error));
        when(rule2.validate(courseId)).thenReturn(List.of());

        TaskValidationException ex = assertThrows(
                TaskValidationException.class,
                () -> validator.validate(courseId)
        );

        assertEquals(1, ex.getErrors().size());
        assertEquals("field", ex.getErrors().getFirst().getField());
        assertEquals("CODE", ex.getErrors().getFirst().getCode());
        assertEquals("Some error", ex.getErrors().getFirst().getMessage());

        verify(rule1, times(1)).validate(courseId);
        verify(rule2, times(1)).validate(courseId);
    }
}