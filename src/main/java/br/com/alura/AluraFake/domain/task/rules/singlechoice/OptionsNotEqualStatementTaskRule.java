package br.com.alura.AluraFake.domain.task.rules.singlechoice;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.course.dto.TaskAnswerDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.util.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@TaskTypeAllowed({Type.SINGLE_CHOICE,Type.MULTIPLE_CHOICE})
public class OptionsNotEqualStatementTaskRule implements TaskRule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if (taskRequest.getOptions() == null || taskRequest.getStatement() == null) return List.of();

        String normalizedStatement = taskRequest.getStatement().replaceAll("\\s+", "").toLowerCase();

        for (TaskAnswerDTO option : taskRequest.getOptions()) {
            String normalizedOption = option.getOption().replaceAll("\\s+", "").toLowerCase();
            if (normalizedOption.equals(normalizedStatement)) {
                return List.of(new ValidationError(
                        "options",
                        "OPTION_EQUALS_STATEMENT",
                        "Options must not be equal to the task statement."
                ));
            }
        }
        return List.of();
    }
}
