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
@TaskTypeAllowed({Type.SINGLE_CHOICE,Type.MULTIPLE_CHOICE})
public class OptionsNotEqualStatementRule implements Rule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if (taskRequest.getOptions() == null || taskRequest.getStatement() == null) return List.of();

        String normalizedStatement = taskRequest.getStatement().replaceAll("\\s+", "").toLowerCase();

        for (TaskOptionsDTO option : taskRequest.getOptions()) {
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
