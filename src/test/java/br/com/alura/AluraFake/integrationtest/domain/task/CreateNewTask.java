package br.com.alura.AluraFake.integrationtest.domain.task;

import br.com.alura.AluraFake.api.dto.task.TaskDTO;
import br.com.alura.AluraFake.domain.enumeration.Type;
import br.com.alura.AluraFake.integrationtest.base.IntegrationTestBase;
import br.com.alura.AluraFake.integrationtest.helper.TaskHelper;
import br.com.alura.AluraFake.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CreateNewTask extends IntegrationTestBase {

    @Autowired
    private TaskService taskService;

    private TaskDTO task = new TaskDTO();

    @Test
    @DisplayName("must Register TaskSuccessfully")
    public void shouldCreateNewTask() {
        givenAvalidTaskSingleChoice();
        whenIRegisterTheTaskThenItShouldBeRegisteredWithoutErrors();
    }

    void givenAvalidTaskSingleChoice() {
        task.setCourseId(1);
        task.setOrder(1);
        task.setStatement("Pergunta Teste");
        task.setType(Type.SINGLE_CHOICE);
        task.setOptions(TaskHelper.getSingleCoiceTaskAswer());
    }

    public void whenIRegisterTheTaskThenItShouldBeRegisteredWithoutErrors() {
        assertDoesNotThrow(() -> {
            taskService.createNewTask(task);
        });
    }

}
