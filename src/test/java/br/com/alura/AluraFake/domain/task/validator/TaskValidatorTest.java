package br.com.alura.AluraFake.domain.task.validator;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.error.exception.TaskValidationException;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.task.rules.TaskTypeAllowed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskValidatorTest {

    private TaskValidator validator;

    @TaskTypeAllowed({Type.SINGLE_CHOICE})
    record SingleChoiceRule(List<ValidationError> errorsToReturn) implements TaskRule {
        @Override
        public List<ValidationError> validate(TaskDTO taskRequest) {
            return errorsToReturn;
        }
    }

    record NoTypeRule(List<ValidationError> errorsToReturn) implements TaskRule {
        @Override
        public List<ValidationError> validate(TaskDTO taskRequest) {
            return errorsToReturn;
        }
    }

    private TaskRule rule1;
    private TaskRule rule2;

    @BeforeEach
    void setup() {
        rule1 = new SingleChoiceRule(List.of());
        rule2 = new NoTypeRule(List.of());

        validator = new TaskValidator(List.of(rule1, rule2));
    }

    @Test
    void shouldValidateWithoutErrors() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);

        List<ValidationError> errors = validator.validate(task);

        assertTrue(errors.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenRuleReturnsError() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.SINGLE_CHOICE);

        rule1 = new SingleChoiceRule(List.of(new ValidationError("field", "CODE", "Message")));
        rule2 = new NoTypeRule(List.of());
        validator = new TaskValidator(List.of(rule1, rule2));

        TaskValidationException exception = assertThrows(TaskValidationException.class, () -> validator.validate(task));

        assertEquals(1, exception.getErrors().size());
        assertEquals("CODE", exception.getErrors().get(0).getCode());
    }

    @Test
    void shouldSkipRulesForOtherTypes() {
        TaskDTO task = new TaskDTO();
        task.setType(Type.MULTIPLE_CHOICE);

        List<ValidationError> errors = validator.validate(task);

        assertTrue(errors.isEmpty());
    }
}

