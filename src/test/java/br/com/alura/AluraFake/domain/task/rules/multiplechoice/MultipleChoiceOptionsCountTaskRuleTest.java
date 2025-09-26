package br.com.alura.AluraFake.domain.task.rules.multiplechoice;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultipleChoiceOptionsCountTaskRuleTest {

    private final MultipleChoiceOptionsCountTaskRule rule = new MultipleChoiceOptionsCountTaskRule();

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
    void shouldReturnErrorWhenLessThanThreeOptions() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 2", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("INVALID_OPTIONS_COUNT", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenMoreThanFiveOptions() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 2", false),
                new TaskAnswerDTO("Option 3", false),
                new TaskAnswerDTO("Option 4", false),
                new TaskAnswerDTO("Option 5", false),
                new TaskAnswerDTO("Option 6", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("INVALID_OPTIONS_COUNT", errors.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenOptionsBetweenThreeAndFive() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of(
                new TaskAnswerDTO("Option 1", true),
                new TaskAnswerDTO("Option 2", false),
                new TaskAnswerDTO("Option 3", false),
                new TaskAnswerDTO("Option 4", false)
        ));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }
}
