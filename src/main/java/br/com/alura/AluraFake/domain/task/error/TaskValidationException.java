package br.com.alura.AluraFake.domain.task.error;

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
