package br.com.alura.AluraFake.api.dto.task;

import br.com.alura.AluraFake.domain.enumeration.Type;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TaskDTO {

    @NotNull
    private Long courseId;

    @NotNull
    @NotBlank
    private String statement;

    @NotNull
    private Integer order;

    @NotNull
    private Type type;

    private List<TaskAnswerDTO> options;

    public TaskDTO(Long courseId, Integer order, String statement, Type type, List<TaskAnswerDTO> options) {
        this.courseId = courseId;
        this.order = order;
        this.statement = statement;
        this.type = type;
        this.options = options;
    }

    public TaskDTO() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<TaskAnswerDTO> getOptions() {
        return options;
    }

    public void setOptions(List<TaskAnswerDTO> options) {
        this.options = options;
    }
}
