package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.infra.repository.CourseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CourseExistsTaskRule implements TaskRule {

    private final CourseRepository courseRepository;

    CourseExistsTaskRule(CourseRepository repository) {
        this.courseRepository = repository;
    }

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        Optional<Course> course = courseRepository.findById(taskRequest.getCourseId());

        if(!course.isPresent()) {
            throw new EntityNotFoundException("The specified course could not be found.");
        }

       return List.of();
    }
}
