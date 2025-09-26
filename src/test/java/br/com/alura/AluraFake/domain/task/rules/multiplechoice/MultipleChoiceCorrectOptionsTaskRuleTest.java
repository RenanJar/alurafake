package br.com.alura.AluraFake.domain.task.rules.multiplechoice;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultipleChoiceCorrectOptionsTaskRuleTest {

    private final MultipleChoiceCorrectOptionsTaskRule rule = new MultipleChoiceCorrectOptionsTaskRule();

    @Test
    void shouldReturnNoErrorWhenOptionsIsNullOrEmpty() {
        TaskDTO task1 = new TaskDTO();
        task1.setType(Type.MULTIPLE_CHOICE);
        task1.setOptions(null);

        TaskDTO task2 = new TaskDTO();
        task2.setType(Type.MULTIPLE_CHOICE);
        task2.setOptions(List.of());

        assertTrue(rule.validate(task1).isEmpty());
        assertTrue(rule.validate(task2).isEmpty());
    }

    @Test
    void shouldReturnErrorWhenLessThanTwoCorrectOptions() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 2", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("INVALID_CORRECT_INCORRECT_OPTIONS", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenNoIncorrectOption() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 2", true)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("INVALID_CORRECT_INCORRECT_OPTIONS", errors.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenAtLeastTwoCorrectAndOneIncorrect() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 2", true),
                new TaskAnswerDTO("Option 3", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }
}
