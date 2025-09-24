package br.com.alura.AluraFake.domain.rules.singlechoice;

import br.com.alura.AluraFake.course.dto.TaskDTO;
import br.com.alura.AluraFake.course.dto.TaskOptionsDTO;
import br.com.alura.AluraFake.domain.rules.Rule;
import br.com.alura.AluraFake.domain.task.error.ValidationError;
import br.com.alura.AluraFake.task.Type;
import br.com.alura.AluraFake.util.TaskTypeAllowed;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@TaskTypeAllowed({Type.MULTIPLE_CHOICE,Type.SINGLE_CHOICE})
public class OptionsDistinctRule implements Rule {

    @Override
    public List<ValidationError> validate(TaskDTO taskRequest) {
        if (taskRequest.getOptions() == null) return List.of();

        Set<String> distinct = new HashSet<>();
        for (TaskOptionsDTO option : taskRequest.getOptions()) {
            if (!distinct.add(option.getOption().trim().toLowerCase())) {
                return List.of(new ValidationError(
                        "options",
                        "DUPLICATE_OPTION",
                        "Options must not be duplicated."
                ));
            }
        }
        return List.of();
    }

}
