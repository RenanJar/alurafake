package br.com.alura.AluraFake.util;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.Task;
import br.com.alura.AluraFake.task.Type;

import java.util.Optional;

public class TaskMapper {

    public static Task toEntity(TaskDTO request) {
        Task task = new Task();

        Optional.ofNullable(request.getOrder()).ifPresent(task::setOrder);
        Optional.ofNullable(request.getStatement()).ifPresent(task::setStatement);
        task.setTypeTask(Type.valueOf(request.getType()));

        return task;
    }
}
