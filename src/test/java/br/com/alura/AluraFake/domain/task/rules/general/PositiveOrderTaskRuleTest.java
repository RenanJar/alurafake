package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PositiveOrderTaskRuleTest {

    private final PositiveOrderTaskRule rule = new PositiveOrderTaskRule();

    @Test
    void shouldReturnErrorWhenOrderIsNull() {
        TaskDTO task = new TaskDTO();
        task.setOrder(null);

        List<ValidationError> errors = rule.validate(task);

        assertEquals(1, errors.size());
        assertEquals("ORDER_NOT_POSITIVE", errors.get(0).getCode());
    }

    @Test
    void shouldReturnErrorWhenOrderIsZeroOrNegative() {
        TaskDTO task1 = new TaskDTO();
        task1.setOrder(0);

        TaskDTO task2 = new TaskDTO();
        task2.setOrder(-5);

        List<ValidationError> errors1 = rule.validate(task1);
        List<ValidationError> errors2 = rule.validate(task2);

        assertEquals(1, errors1.size());
        assertEquals("ORDER_NOT_POSITIVE", errors1.get(0).getCode());

        assertEquals(1, errors2.size());
        assertEquals("ORDER_NOT_POSITIVE", errors2.get(0).getCode());
    }

    @Test
    void shouldReturnNoErrorWhenOrderIsPositive() {
        TaskDTO task = new TaskDTO();
        task.setOrder(3);

        List<ValidationError> errors = rule.validate(task);

        assertTrue(errors.isEmpty());
    }
}
