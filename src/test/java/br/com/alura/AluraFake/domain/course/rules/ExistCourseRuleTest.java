package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExistCourseRuleTest {

    private CourseService courseService;
    private ExistCourseRule rule;

    @BeforeEach
    void setup() {
        courseService = Mockito.mock(CourseService.class);
        rule = new ExistCourseRule(courseService);
    }

    @Test
    void shouldThrowExceptionWhenCourseDoesNotExist() {
        Long courseId = 1L;
        when(courseService.existsById(courseId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            rule.validate(courseId);
        });

        assertEquals("Course with id 1 not found", exception.getMessage());
        verify(courseService).existsById(courseId);
    }

    @Test
    void shouldReturnNoErrorWhenCourseExists() {
        Long courseId = 2L;
        when(courseService.existsById(courseId)).thenReturn(true);

        List<ValidationError> errors = rule.validate(courseId);

        assertTrue(errors.isEmpty());
        verify(courseService).existsById(courseId);
    }
}
