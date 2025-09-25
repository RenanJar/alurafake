package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatementLengthTaskRule implements TaskRule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        String statement = taskRequest.getStatement();

        if (statement == null || statement.length() < 4 || statement.length() > 255) {
            return List.of(
                    new ValidationError(
                            "statement",
                            "TASK_STATEMENT_LENGTH_INVALID",
                            "The statement must be between 4 and 255 characters long."
                    )
            );
        }

        return List.of();
    }
}
