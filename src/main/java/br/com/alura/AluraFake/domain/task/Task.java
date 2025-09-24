package br.com.alura.AluraFake.domain.task;

import br.com.alura.AluraFake.domain.Course;
import br.com.alura.AluraFake.task.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 255)
    @Column(nullable = false)
    private String statement;

    @Column(nullable = false, name = "task_order")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", nullable = false)
    private Type typeTask;

    public Task(Integer id, String statement, Integer order, Course course, Type typeTask) {
        this.id = id;
        this.statement = statement;
        this.order = order;
        this.course = course;
        this.typeTask = typeTask;
    }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Type getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(Type typeTask) {
        this.typeTask = typeTask;
    }
}
