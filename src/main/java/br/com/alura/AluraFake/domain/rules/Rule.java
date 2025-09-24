package br.com.alura.AluraFake.domain.rules;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.domain.task.error.ValidationError;

import java.util.List;

public interface Rule {

    public List<ValidationError> validate (TaskDTO taskRequest);
}
