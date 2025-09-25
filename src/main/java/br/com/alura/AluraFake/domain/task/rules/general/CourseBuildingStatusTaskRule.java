package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.repository.CourseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseBuildingStatusTaskRule implements TaskRule {

    private final CourseRepository courseRepository;

    CourseBuildingStatusTaskRule(CourseRepository repository) {
        this.courseRepository = repository;
    }

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        Optional<Course> course = courseRepository.findById(taskRequest.getCourseId());

        if (!course.isPresent()) {
            return List.of();
        }

        if (!"BUILDING".equalsIgnoreCase(course.get().getStatus().toString())) {
            return List.of(new ValidationError(
                    "course.status",
                    "COURSE_NOT_BUILDING",
                    "The course must be in BUILDING status to receive tasks."
            ));
        }

        return List.of();
    }

}
