package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.infra.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseExistsTaskRuleTest {

    private CourseRepository courseRepository;
    private CourseExistsTaskRule rule;

    @BeforeEach
    void setup() {
        courseRepository = Mockito.mock(CourseRepository.class);
        rule = new CourseExistsTaskRule(courseRepository);
    }

    @Test
    void shouldReturnErrorWhenCourseNotFound() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(1);

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("COURSE_NOT_FOUND", errors.get(0).getCode());
        verify(courseRepository).findById(1);
    }

    @Test
    void shouldReturnNoErrorWhenCourseExists() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(2);

        Course course = new Course();
        when(courseRepository.findById(2)).thenReturn(Optional.of(course));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
        verify(courseRepository).findById(2);
    }
}
