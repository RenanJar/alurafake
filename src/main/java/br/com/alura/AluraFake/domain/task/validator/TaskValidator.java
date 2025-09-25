package br.com.alura.AluraFake.domain.task.validator;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.TaskValidationException;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.task.rules.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskValidator {

    private final List<TaskRule> taskRules;

    public TaskValidator(List<TaskRule> taskRules) {
        this.taskRules = taskRules;
    }

    public List<ValidationError> validate(TaskDTO task) {
        List<ValidationError> result = taskRules.stream()
                .filter(rule -> {
                    TaskTypeAllowed annotation = rule.getClass().getAnnotation(TaskTypeAllowed.class);
                    return annotation == null ||
                            List.of(annotation.value()).contains(task.getType());
                })
                .flatMap(rule -> rule.validate(task).stream())
                .toList();

        if (!result.isEmpty()) {
            throw new TaskValidationException(result);
        }
        return result;
    }
}
