package br.com.alura.AluraFake.domain.task.validator;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.rules.Rule;
import br.com.alura.AluraFake.domain.task.error.TaskValidationException;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.util.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Validator {

    private final List<Rule> rules;

    public Validator(List<Rule> rules) {
        this.rules = rules;
    }

    public List<ValidationError> validate(TaskDTO task) {
        return rules.stream()
                .filter(rule -> {
                    TaskTypeAllowed annotation = rule.getClass().getAnnotation(TaskTypeAllowed.class);
                    return annotation == null ||
                            Stream.of(annotation.value()).map(Type::name)
                            .collect(Collectors.toSet())
                            .contains(task.getType());
                })
                .flatMap(rule -> rule.validate(task).stream())
                .collect(Collectors.toList());
    }
}
