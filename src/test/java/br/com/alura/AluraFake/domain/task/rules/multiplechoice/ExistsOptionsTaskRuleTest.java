package br.com.alura.AluraFake.domain.task.rules.multiplechoice;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExistsOptionsTaskRuleTest {

    private final ExistsOptionsTaskRule rule = new ExistsOptionsTaskRule();

    @Test
    void shouldReturnErrorWhenOptionsIsNull() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(null);

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("OPTIONS_REQUIRED", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenOptionsIsEmpty() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);
        task.setOptions(List.of());

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("OPTIONS_REQUIRED", errors.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenOptionsArePresent() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(List.of(new TaskAnswerDTO("Option 1", true)));

        List<ValidationError> errors = rule.validate(task);

        assertTrue(!errors.isEmpty());
    }
}