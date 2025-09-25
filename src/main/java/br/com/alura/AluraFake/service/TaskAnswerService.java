package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.domain.course.dto.TaskAnswerDTO;
import br.com.alura.AluraFake.infra.repository.TaskAnswerRepository;
import br.com.alura.AluraFake.domain.task.util.TaskAnswerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskAnswerService {

    private final TaskAnswerRepository taskAnswerRepository;
    private final TaskAnswerMapper taskAnswerMapper;

    public TaskAnswerService(TaskAnswerRepository taskAnswerRepository, TaskAnswerMapper taskAnswerMapper) {
        this.taskAnswerRepository = taskAnswerRepository;
        this.taskAnswerMapper = taskAnswerMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveList(List<TaskAnswerDTO> taskAnswerDTO, Integer taskId) {
        try{
            taskAnswerDTO.stream()
                    .map(dto->taskAnswerMapper.toEntity(dto, taskId))
                    .forEach(taskAnswerRepository::save);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
