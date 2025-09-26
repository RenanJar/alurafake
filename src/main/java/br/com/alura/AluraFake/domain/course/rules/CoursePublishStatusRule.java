package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.service.CourseService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoursePublishStatusRule implements CourseRule {

    private final CourseService courseService;

    public CoursePublishStatusRule(@Lazy CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public List<ValidationError> validate(Long courseId) {

        if (courseService.verifyStatus(courseId, Status.PUBLISHED)) {
            return List.of(new ValidationError(
                    "course",
                    "INVALID_COURSE_STATUS",
                    "Course can only be published if status is BUILDING."
            ));
        }

        return List.of();
    }

}
