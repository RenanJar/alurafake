package br.com.alura.AluraFake.domain.rules.multiplechoice;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.rules.Rule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.util.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@TaskTypeAllowed({Type.MULTIPLE_CHOICE})
public class MultipleChoiceOptionsCountRule implements Rule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {

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
