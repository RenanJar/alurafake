package br.com.alura.AluraFake.domain.task.rules.singlechoice;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinMaxOptionsCountTaskRuleTest {

    private final MinMaxOptionsCountTaskRule rule = new MinMaxOptionsCountTaskRule();

    @Test
    void shouldReturnErrorWhenOptionsIsNull() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(null);

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("INVALID_OPTIONS_COUNT", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenOptionsSizeLessThan2() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(new TaskAnswerDTO()));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("INVALID_OPTIONS_COUNT", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenOptionsSizeGreaterThan5() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(new TaskAnswerDTO(), new TaskAnswerDTO(), new TaskAnswerDTO(), new TaskAnswerDTO(), new TaskAnswerDTO(), new TaskAnswerDTO()));

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("INVALID_OPTIONS_COUNT", errors.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenOptionsSizeBetween2And5() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(new TaskAnswerDTO(), new TaskAnswerDTO(), new TaskAnswerDTO()));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }
}