package br.com.alura.AluraFake.integrationtest.helper;

import br.com.alura.AluraFake.api.dto.task.TaskAnswerDTO;

import java.util.List;

public class TaskHelper {

    public static List<TaskAnswerDTO> getSingleCoiceTaskAswer() {
        var taskAswer1 = new TaskAnswerDTO();
        taskAswer1.setOption("Resposta Incorreta 1");
        taskAswer1.setIsCorrect(false);

        var taskAswer2 = new TaskAnswerDTO();
        taskAswer2.setOption("Resposta Incorreta 2");
        taskAswer2.setIsCorrect(false);

        var taskAswer3 = new TaskAnswerDTO();
        taskAswer3.setOption("Resposta correta");
        taskAswer3.setIsCorrect(true);

        return List.of(taskAswer1, taskAswer2, taskAswer3);
    }

    public static List<TaskAnswerDTO> getMultipleCoiceTaskAswer() {
        var taskAswer1 = new TaskAnswerDTO();
        taskAswer1.setOption("Resposta Incorreta 1");
        taskAswer1.setIsCorrect(false);

        var taskAswer2 = new TaskAnswerDTO();
        taskAswer2.setOption("Resposta correta 2");
        taskAswer2.setIsCorrect(true);

        var taskAswer3 = new TaskAnswerDTO();
        taskAswer3.setOption("Resposta correta 1");
        taskAswer3.setIsCorrect(true);

        return List.of(taskAswer1, taskAswer2, taskAswer3);
    }

}
