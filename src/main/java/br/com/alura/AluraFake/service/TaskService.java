package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.entity.Task;
import br.com.alura.AluraFake.domain.task.validator.TaskValidator;
import br.com.alura.AluraFake.repository.TaskRepository;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.util.TaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskAnswerService taskAnswerService;
    private final TaskRepository taskRepository;
    private final TaskValidator taskValidator;
    private final TaskMapper taskMapper;

    public TaskService(TaskAnswerService taskAnswerService, TaskRepository taskRepository, TaskValidator taskValidator, TaskMapper taskMapper) {
        this.taskAnswerService = taskAnswerService;
        this.taskRepository = taskRepository;
        this.taskValidator = taskValidator;
        this.taskMapper = taskMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer createNewTask(TaskDTO request) {
        taskValidator.validate(request);

        Task newTask = taskMapper.toEntity(request);
        taskRepository.updateOrder(request.getCourseId(), request.getOrder());
        taskRepository.save(newTask);
        saveOptions(request, newTask.getId());

        return newTask.getId();
    }

    private boolean saveOptions(TaskDTO request, Integer taskId) {
        if (request.getType().equals(Type.MULTIPLE_CHOICE) || request.getType().equals(Type.SINGLE_CHOICE)) {
            return taskAnswerService.saveList(request.getOptions(), taskId);
        }
        return false;
    }

    public List<TaskDTO> findByCourseIdOrderByOrderAsc(Integer courseId) {
        return taskRepository.findByCourseIdOrderByOrderAsc(courseId).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public List<TaskDTO> findByCourseId(Integer courseId) {
        return taskRepository.findByCourseId(courseId).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public boolean hasAllTaskTypes(Long courseId) {
        return !(taskRepository.hasAllTaskTypes(courseId).size() == Type.values().length);
    }

    public Boolean isTaskOrderContinuous(Long courseId) {
        return taskRepository.isTaskOrderContinuous(courseId);
    }
}
