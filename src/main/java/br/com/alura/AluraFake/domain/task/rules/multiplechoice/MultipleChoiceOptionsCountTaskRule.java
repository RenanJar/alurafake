package br.com.alura.AluraFake.domain.task.rules.multiplechoice;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.task.rules.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@TaskTypeAllowed({Type.MULTIPLE_CHOICE})
public class MultipleChoiceOptionsCountTaskRule implements TaskRule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if(Objects.isNull(taskRequest.getOptions()) || taskRequest.getOptions().isEmpty()) return List.of();

        int size = taskRequest.getOptions().size();
        if (size < 3 || size > 5) {
            return List.of(new ValidationError(
                    "options",
                    "INVALID_OPTIONS_COUNT",
                    "Multiple choice task must have between 3 and 5 options."
            ));
        }

        return List.of();
    }

}
