package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.error.EntityNotFoundException;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.service.CourseService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExistCourseRule implements CourseRule {

    private final CourseService courseService;

    public ExistCourseRule(@Lazy CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public List<ValidationError> validate(Long courseId) {

        boolean exists = courseService.existsById(courseId);

        if (!exists) {
            throw new EntityNotFoundException("Course with id " + courseId + " not found");
        }

        return List.of();
    }
}
