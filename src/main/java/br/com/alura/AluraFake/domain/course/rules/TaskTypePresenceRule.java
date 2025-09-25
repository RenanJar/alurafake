package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.service.TaskService;
import br.com.alura.AluraFake.task.Type;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskTypePresenceRule implements CourseRule {

    private final TaskService taskService;

    public TaskTypePresenceRule(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<ValidationError> validate(Long courseId) {

        if (taskService.hasAllTaskTypes(courseId)) {
            return List.of(new ValidationError(
                    "tasks",
                    "MISSING_TASK_TYPE",
                    "Course must have at least one task of each type"
            ));
        }

        return List.of();
    }

}
