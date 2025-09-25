package br.com.alura.AluraFake.domain.task.rules.multiplechoice;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.task.rules.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@TaskTypeAllowed({Type.SINGLE_CHOICE,Type.MULTIPLE_CHOICE})
public class ExistsOptionsTaskRule implements TaskRule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if(Objects.isNull(taskRequest.getOptions()) || taskRequest.getOptions().isEmpty()) {
            return List.of(
                    new ValidationError(
                            "options",
                            "OPTIONS_REQUIRED",
                            "Options must have at least one option."
                    )
            );
        }

       return List.of();
    }
}
