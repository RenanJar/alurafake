package br.com.alura.AluraFake.domain.task.rules.general;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.infra.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseBuildingStatusTaskRuleTest {

    private CourseRepository courseRepository;
    private CourseBuildingStatusTaskRule rule;

    @BeforeEach
    void setup() {
        courseRepository = Mockito.mock(CourseRepository.class);
        rule = new CourseBuildingStatusTaskRule(courseRepository);
    }

    @Test
    void shouldReturnNoErrorWhenCourseNotFound() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(1);

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
        verify(courseRepository).findById(1);
    }

    @Test
    void shouldReturnErrorWhenCourseIsNotBuilding() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(2);

        Course course = new Course();
        course.setStatus(Status.PUBLISHED);

        when(courseRepository.findById(2)).thenReturn(Optional.of(course));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("COURSE_NOT_BUILDING", errors.get(0).getCode());
        verify(courseRepository).findById(2);
    }

    @Test
    void shouldReturnNoErrorWhenCourseIsBuilding() {
        TaskDTO task = new TaskDTO();
        task.setCourseId(3);

        Course course = new Course();
        course.setStatus(Status.BUILDING);

        when(courseRepository.findById(3)).thenReturn(Optional.of(course));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
        verify(courseRepository).findById(3);
    }
}
