package br.com.alura.AluraFake.domain.task.rules.general;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.task.rules.TaskRule;
import br.com.alura.AluraFake.service.TaskService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TaskOrderValidationTaskRule implements TaskRule {

    private final TaskService taskService;

    public TaskOrderValidationTaskRule(@Lazy TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        List<TaskDTO> tasks = taskService.findByCourseIdOrderByOrderAsc(taskRequest.getCourseId());

        int maxOrder = tasks.isEmpty() ? 0 : tasks.get(tasks.size() - 1).getOrder();
        if (taskRequest.getOrder() > maxOrder + 1) {
            return List.of(
                    new ValidationError(
                            "order",
                            "INVALID_TASK_ORDER",
                            "Task order is inconsistent. The sequence must be continuous."
                    )
            );
        }

        return List.of();
    }
}
