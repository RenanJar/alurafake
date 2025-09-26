package br.com.alura.AluraFake.api.dto.task;

public class TaskAnswerDTO {

    private String option;

    private Boolean isCorrect;

    public TaskAnswerDTO(String option, boolean isCorrect) {
        this.option = option;
        this.isCorrect = isCorrect;
    }

    public TaskAnswerDTO() {
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
