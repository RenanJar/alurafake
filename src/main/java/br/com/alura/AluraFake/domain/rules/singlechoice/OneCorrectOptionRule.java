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
@TaskTypeAllowed({Type.SINGLE_CHOICE})
public class OneCorrectOptionRule implements Rule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        long correctCount = taskRequest.getOptions().stream().filter(TaskOptionsDTO::isCorrect).count();
        if (correctCount != 1) {
            return List.of(new ValidationError(
                    "options",
                    "INVALID_CORRECT_OPTION_COUNT",
                    "Single choice task must have exactly one correct option."
            ));
        }
        return List.of();
    }
}
