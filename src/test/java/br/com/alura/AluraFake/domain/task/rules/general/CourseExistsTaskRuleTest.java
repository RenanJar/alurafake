package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.infra.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        task.setCourseId(1L);

        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->{rule.validate(task);});

        verify(courseRepository).findById(1L);
    }

    @Test
    void shouldReturnNoErrorWhenCourseExists() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(2L);

        Course course = new Course();
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
        verify(courseRepository).findById(2L);
    }
}
