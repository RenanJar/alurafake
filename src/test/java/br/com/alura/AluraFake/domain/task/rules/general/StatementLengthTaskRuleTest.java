package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StatementLengthTaskRuleTest {

    private final StatementLengthTaskRule rule = new StatementLengthTaskRule();

    @Test
    void shouldReturnErrorWhenStatementIsNull() {
        TaskDTO task = new TaskDTO();
        task.setStatement(null);

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("TASK_STATEMENT_LENGTH_INVALID", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenStatementTooShort() {
        TaskDTO task = new TaskDTO();
        task.setStatement("abc");

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("TASK_STATEMENT_LENGTH_INVALID", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenStatementTooLong() {
        TaskDTO task = new TaskDTO();
        task.setStatement("a".repeat(256));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("TASK_STATEMENT_LENGTH_INVALID", errors.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenStatementLengthIsValid() {
        TaskDTO task = new TaskDTO();
        task.setStatement("This is a valid task statement.");

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }
}
