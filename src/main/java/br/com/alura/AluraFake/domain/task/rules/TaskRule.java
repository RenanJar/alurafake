package br.com.alura.AluraFake.domain.task.rules;

import br.com.alura.AluraFake.domain.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.error.ValidationError;

import java.util.List;

public interface TaskRule {

    public List<ValidationError> validate (TaskDTO taskRequest);
}
