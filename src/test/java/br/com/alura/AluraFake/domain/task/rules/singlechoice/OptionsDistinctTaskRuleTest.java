package br.com.alura.AluraFake.domain.task.rules.singlechoice;
import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsDistinctTaskRuleTest {

    private final OptionsDistinctTaskRule rule = new OptionsDistinctTaskRule();

    @Test
    void shouldReturnNoErrorWhenOptionsIsNull() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(null);

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldReturnErrorWhenOptionsAreDuplicated() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 1", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("DUPLICATE_OPTION", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenOptionsAreDuplicatedIgnoringCaseAndSpaces() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO(" option 1 ", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("DUPLICATE_OPTION", errors.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenAllOptionsAreDistinct() {
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
