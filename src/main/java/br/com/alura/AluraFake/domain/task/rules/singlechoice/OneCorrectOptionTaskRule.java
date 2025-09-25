package br.com.alura.AluraFake.domain.task.rules.singlechoice;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.course.dto.TaskAnswerDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.util.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@TaskTypeAllowed({Type.SINGLE_CHOICE})
public class OneCorrectOptionTaskRule implements TaskRule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if (Objects.isNull(taskRequest.getOptions())) {return List.of();}

        long correctCount = taskRequest.getOptions().stream().filter(TaskAnswerDTO::isCorrect).count();
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
