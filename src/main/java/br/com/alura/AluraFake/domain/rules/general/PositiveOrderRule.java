package br.com.alura.AluraFake.domain.rules.general;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.rules.Rule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositiveOrderRule implements Rule {

    @Override
    public List<ValidationError> validate(TaskDTO task) {
        if (task.getOrder() == null || task.getOrder() <= 0) {
            return List.of(new ValidationError(
                    "order",
                    "ORDER_NOT_POSITIVE",
                    "The order must be a positive integer."
            ));
        }
        return List.of();
    }
}
