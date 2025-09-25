package br.com.alura.AluraFake.domain.course.validator;

import br.com.alura.AluraFake.domain.course.rules.CourseRule;
import br.com.alura.AluraFake.domain.error.TaskValidationException;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseValidator {

    private final List<CourseRule> rules;

    public CourseValidator(List<CourseRule> rules) {
        this.rules = rules;
    }

    public List<ValidationError> validate(Long courseId) {
        List<ValidationError> result = rules.stream()
                .flatMap(rule -> rule.validate(courseId).stream())
                .toList();

        if (!result.isEmpty()) {
            throw new TaskValidationException(result);
        }
        return result;
    }
}
