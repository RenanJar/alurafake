package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CoursePublishStatusRuleTest {

    private CourseService courseService;
    private CoursePublishStatusRule rule;

    @BeforeEach
    void setup() {
        courseService = Mockito.mock(CourseService.class);
        rule = new CoursePublishStatusRule(courseService);
    }

    @Test
    void shouldReturnErrorWhenCourseAlreadyPublished() {
        Long courseId = 1L;

        when(courseService.verifyStatus(courseId, Status.PUBLISHED)).thenReturn(true);

        List<ValidationError> errors = rule.validate(courseId);

        assertEquals(1, errors.size());
        assertEquals("INVALID_COURSE_STATUS", errors.get(0).getCode());
        verify(courseService).verifyStatus(courseId, Status.PUBLISHED);
    }

    @Test
    void shouldReturnNoErrorWhenCourseIsNotPublished() {
        Long courseId = 2L;

        when(courseService.verifyStatus(courseId, Status.PUBLISHED)).thenReturn(false);

        List<ValidationError> errors = rule.validate(courseId);

        assertTrue(errors.isEmpty());
        verify(courseService).verifyStatus(courseId, Status.PUBLISHED);
    }
}
