package br.com.alura.AluraFake.domain.task.entity;

import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.enumeration.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 255)
    @Column(nullable = false)
    private String statement;

    @Column(nullable = false, name = "task_order")
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "course_id", insertable = false, updatable = false)
    private Long courseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", nullable = false)
    private Type typeTask;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskAnswer> taskAnswers = new ArrayList<>();

    public Task(Long id, String statement, Integer order, Course course, Type typeTask) {
        this.id = id;
        this.statement = statement;
        this.order = order;
        this.course = course;
        this.typeTask = typeTask;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getCourseId() {
        return courseId;
    }

    public Type getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(Type typeTask) {
        this.typeTask = typeTask;
    }

    public List<TaskAnswer> getTaskAnswers() {
        return taskAnswers;
    }

    public void setTaskAnswers(List<TaskAnswer> taskAnswers) {
        this.taskAnswers = taskAnswers;
    }

    public void addAnswer(TaskAnswer answer) {
        answer.setTask(this);
        this.taskAnswers.add(answer);
    }
}
