package br.com.alura.AluraFake.domain.rules.general;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.rules.Rule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UniqueStatementRule implements Rule {

    private final TaskRepository taskRepository;

    UniqueStatementRule(TaskRepository repository) {
        this.taskRepository = repository;
    }

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        String normalizedStatement = taskRequest.getStatement().replaceAll("\\s+", "").toLowerCase();

        boolean exists = taskRepository.findByCourseId(taskRequest.getCourseId()).stream()
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
