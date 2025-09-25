package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskOrderContinuousRule implements CourseRule {

    private final TaskService taskService;

    public TaskOrderContinuousRule(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<ValidationError> validate(Long courseId) {
        boolean continuous = taskService.isTaskOrderContinuous(courseId);

        if (!continuous) {
            return List.of(new ValidationError(
                    "tasks",
                    "TASK_ORDER_INVALID",
                    "Task orders must be continuous starting from 1"
            ));
        }

        return List.of();
    }

}
