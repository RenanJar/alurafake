package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositiveOrderTaskRule implements TaskRule {

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
