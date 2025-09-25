package br.com.alura.AluraFake.domain.error;

import br.com.alura.AluraFake.domain.error.dto.ValidationError;

import java.util.List;

public class TaskValidationException extends RuntimeException {
    private final List<ValidationError> errors;

    public TaskValidationException(List<ValidationError> errors) {
        super("Task validation failed");
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
