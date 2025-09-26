package br.com.alura.AluraFake.domain.task.rules.singlechoice;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionLengthTaskRuleTest {

    private final OptionLengthTaskRule rule = new OptionLengthTaskRule();

    @Test
    void shouldReturnNoErrorWhenOptionsIsNull() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(null);

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenOptionIsTooShort() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("abc", true),
                new TaskAnswerDTO("Valid Option", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("OPTION_LENGTH_INVALID", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenOptionIsTooLong() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        String longOption = "x".repeat(81);
        task.setOptions(List.of(
                new TaskAnswerDTO(longOption, true),
                new TaskAnswerDTO("Valid Option", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("OPTION_LENGTH_INVALID", errors.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenAllOptionsHaveValidLength() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 2", false),
                new TaskAnswerDTO("Option 3", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }
}
