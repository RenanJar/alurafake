package br.com.alura.AluraFake.domain.task.rules.multiplechoice;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.course.dto.TaskAnswerDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.task.rules.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@TaskTypeAllowed({Type.MULTIPLE_CHOICE})
public class MultipleChoiceCorrectOptionsTaskRule implements TaskRule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if (taskRequest.getOptions() == null || taskRequest.getOptions().isEmpty()) return List.of();

        long correctCount = taskRequest.getOptions().stream().filter(TaskAnswerDTO::isCorrect).count();
        long incorrectCount = taskRequest.getOptions().size() - correctCount;

        if (correctCount < 2 || incorrectCount < 1) {
            return List.of(new ValidationError(
                    "options",
                    "INVALID_CORRECT_INCORRECT_OPTIONS",
                    "Multiple choice task must have at least two correct options and at least one incorrect option."
            ));
        }

        return List.of();

    }
}
