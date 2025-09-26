package br.com.alura.AluraFake.domain.task.rules;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;

import java.util.List;

public interface TaskRule {

    public List<ValidationError> validate (TaskDTO taskRequest);
}
