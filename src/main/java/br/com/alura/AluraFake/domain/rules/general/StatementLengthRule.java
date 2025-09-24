package br.com.alura.AluraFake.domain.rules.general;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.rules.Rule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class StatementLengthRule implements Rule {

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
