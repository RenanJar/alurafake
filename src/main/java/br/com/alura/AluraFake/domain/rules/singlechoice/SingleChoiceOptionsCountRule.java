package br.com.alura.AluraFake.domain.rules.singlechoice;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.rules.Rule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.util.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@TaskTypeAllowed({Type.SINGLE_CHOICE})
public class SingleChoiceOptionsCountRule implements Rule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {

        int size = taskRequest.getOptions() != null ? taskRequest.getOptions().size() : 0;
        if (size < 2 || size > 5) {
            return List.of(new ValidationError(
                    "options",
                    "INVALID_OPTIONS_COUNT",
                    "Single choice task must have between 2 and 5 options."
            ));
        }
        return List.of();
    }
}
