package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.Task;
import br.com.alura.AluraFake.domain.task.validator.Validator;
import br.com.alura.AluraFake.repository.TaskRepository;
import br.com.alura.AluraFake.util.TaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final Validator validator;

    public TaskService(TaskRepository taskRepository, Validator validator) {
        this.taskRepository = taskRepository;
        this.validator = validator;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer createNewTask(TaskDTO request){
        validator.validate(request);

        Task newTask = TaskMapper.toEntity(request);

        return taskRepository.save(newTask).getId();
    }

}
