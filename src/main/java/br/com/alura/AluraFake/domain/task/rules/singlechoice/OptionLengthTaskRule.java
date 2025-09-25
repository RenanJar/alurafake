package br.com.alura.AluraFake.domain.task.rules.singlechoice;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.course.dto.TaskAnswerDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.task.rules.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@TaskTypeAllowed({Type.MULTIPLE_CHOICE,Type.SINGLE_CHOICE})
public class OptionLengthTaskRule implements TaskRule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if (taskRequest.getOptions() == null) return List.of();

        for (TaskAnswerDTO option : taskRequest.getOptions()) {
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
