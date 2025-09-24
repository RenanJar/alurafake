package br.com.alura.AluraFake.course.dto;

import java.util.List;

public class TaskDTO {

    private Integer courseId;
    private String statement;
    private Integer order;
    private String type;
    private List<TaskOptionsDTO> options;

    public TaskDTO(Integer courseId, Integer order, String statement, String type, List<TaskOptionsDTO> options) {
        this.courseId = courseId;
        this.order = order;
        this.statement = statement;
        this.type = type;
        this.options = options;
    }

    public TaskDTO() {
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TaskOptionsDTO> getOptions() {
        return options;
    }

    public void setOptions(List<TaskOptionsDTO> options) {
        this.options = options;
    }
}
