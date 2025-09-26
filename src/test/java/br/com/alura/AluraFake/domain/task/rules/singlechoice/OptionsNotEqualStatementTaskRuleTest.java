package br.com.alura.AluraFake.domain.task.rules.singlechoice;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsNotEqualStatementTaskRuleTest {

    private final OptionsNotEqualStatementTaskRule rule = new OptionsNotEqualStatementTaskRule();

    @Test
    void shouldReturnNoErrorWhenOptionsOrStatementIsNull() {
        TaskDTO task1 = new TaskDTO();
        task1.setType(Type.SINGLE_CHOICE);
        task1.setOptions(null);
        task1.setStatement("Some statement");

        TaskDTO task2 = new TaskDTO();
        task2.setType(Type.MULTIPLE_CHOICE);
        task2.setOptions(List.of(new TaskAnswerDTO("Option 1", true)));
        task2.setStatement(null);

        assertTrue(rule.validate(task1).isEmpty());
        assertTrue(rule.validate(task2).isEmpty());
    }

    @Test
    void shouldReturnErrorWhenOptionEqualsStatement() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setStatement("Task Statement");
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Task Statement", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("OPTION_EQUALS_STATEMENT", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenOptionEqualsStatementIgnoringSpacesAndCase() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setStatement("Task Statement");
        task.setOptions(List.of(
                new TaskAnswerDTO("taskstatement", true),
                new TaskAnswerDTO("Option 2", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("OPTION_EQUALS_STATEMENT", errors.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenAllOptionsDifferentFromStatement() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setStatement("Task Statement");
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 2", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }
}
