package br.com.alura.AluraFake.util;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.task.entity.Task;
import br.com.alura.AluraFake.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class TaskMapper {

    @Autowired
    private CourseRepository courseRepository;

    public Task toEntity(TaskDTO request) {
        Task task = new Task();
        Course course = courseRepository.getReferenceById(request.getCourseId());

        Optional.ofNullable(request.getOrder()).ifPresent(task::setOrder);
        Optional.ofNullable(request.getStatement()).ifPresent(task::setStatement);
        task.setTypeTask(request.getType());
        task.setCourse(course);

        return task;
    }

    public TaskDTO toDto(Task task) {

        TaskDTO taskDTO = new TaskDTO();
        Optional.ofNullable(task.getOrder()).ifPresent(taskDTO::setOrder);
        Optional.ofNullable(task.getStatement()).ifPresent(taskDTO::setStatement);
        Optional.ofNullable(task.getTypeTask()).ifPresent(taskDTO::setType);
        Optional.ofNullable(task.getCourseId()).ifPresent(taskDTO::setCourseId);

        return taskDTO;
    }
}
