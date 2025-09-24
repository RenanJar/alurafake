package br.com.alura.AluraFake.domain.rules.singlechoice;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.course.dto.TaskOptionsDTO;
import br.com.alura.AluraFake.domain.rules.Rule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.util.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@TaskTypeAllowed({Type.MULTIPLE_CHOICE,Type.SINGLE_CHOICE})
public class OptionLengthRule implements Rule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if (taskRequest.getOptions() == null) return List.of();

        for (TaskOptionsDTO option : taskRequest.getOptions()) {
            String value = option.getOption();
            if (value == null || value.length() < 4 || value.length() > 80) {
                return List.of(new ValidationError(
                        "options",
                        "OPTION_LENGTH_INVALID",
                        "Each option must have between 4 and 80 characters."
                ));
            }
        }
        return List.of();
    }

}
