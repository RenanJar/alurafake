package br.com.alura.AluraFake.domain.rules.multiplechoice;

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
@TaskTypeAllowed({Type.MULTIPLE_CHOICE})
public class MultipleChoiceCorrectOptionsRule implements Rule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if (taskRequest.getOptions() == null || taskRequest.getOptions().isEmpty()) return List.of();

        long correctCount = taskRequest.getOptions().stream().filter(TaskOptionsDTO::isCorrect).count();
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
