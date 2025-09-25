package br.com.alura.AluraFake.domain.task.util;

import br.com.alura.AluraFake.domain.course.dto.TaskAnswerDTO;
import br.com.alura.AluraFake.domain.task.entity.Task;
import br.com.alura.AluraFake.domain.task.entity.TaskAnswer;
import br.com.alura.AluraFake.infra.repository.TaskRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskAnswerMapper {

    private final TaskRepository taskRepository;

    public TaskAnswerMapper(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskAnswer toEntity(TaskAnswerDTO taskAnswerDTO, Integer taskId) {
      TaskAnswer taskAnswer = new TaskAnswer();
      Task task = taskRepository.getReferenceById(taskId);

      taskAnswer.setTask(task);
      taskAnswer.setCorrect(taskAnswerDTO.isCorrect());
      taskAnswer.setOptionText(taskAnswerDTO.getOption());

      return taskAnswer;
    }

}
