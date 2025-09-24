package br.com.alura.AluraFake.course.dto;

public class TaskOptionsDTO {

    private String option;

    private boolean isCorrect;

    public TaskOptionsDTO(String option, boolean isCorrect) {
        this.option = option;
        this.isCorrect = isCorrect;
    }

    public TaskOptionsDTO() {
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
