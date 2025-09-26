package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;
import br.com.alura.AluraFake.infra.repository.TaskAnswerRepository;
import br.com.alura.AluraFake.domain.task.util.TaskAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskAnswerService {

    private static final Logger log = LoggerFactory.getLogger(TaskAnswerService.class);

    private final TaskAnswerRepository taskAnswerRepository;
    private final TaskAnswerMapper taskAnswerMapper;

    public TaskAnswerService(TaskAnswerRepository taskAnswerRepository, TaskAnswerMapper taskAnswerMapper) {
        this.taskAnswerRepository = taskAnswerRepository;
        this.taskAnswerMapper = taskAnswerMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveList(List<TaskAnswerDTO> taskAnswerDTO, Integer taskId) {
        try{
            log.info("starting taskAnswer persistence");
            taskAnswerDTO.stream()
                    .map(dto->taskAnswerMapper.toEntity(dto, taskId))
                    .forEach(taskAnswerRepository::save);
            log.info("persistence finished");
        }catch (Exception e){
            log.info("persistence error");
            return false;
        }
        return true;
    }
}
