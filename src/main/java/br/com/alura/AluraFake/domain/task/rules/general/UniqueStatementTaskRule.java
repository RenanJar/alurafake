package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.service.TaskService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UniqueStatementTaskRule implements TaskRule {

    private final TaskService taskService;

    UniqueStatementTaskRule(@Lazy TaskService repository) {
        this.taskService = repository;
    }

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        String normalizedStatement = taskRequest.getStatement().replaceAll("\\s+", "").toLowerCase();

        boolean exists = taskService.findByCourseId(taskRequest.getCourseId()).stream()
                .map(task -> task.getStatement().replaceAll("\\s+", "").toLowerCase())
                .anyMatch(normalizedStatementDb -> normalizedStatementDb.equals(normalizedStatement));

        if (exists) {
            return List.of(new ValidationError(
                    "statement",
                    "DUPLICATE_STATEMENT",
                    "A task with the same statement already exists in this course."
            ));
        }

        return List.of();
    }
}
