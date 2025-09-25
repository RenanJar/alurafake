package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.course.entity.Course;
import br.com.alura.AluraFake.domain.task.error.ValidationError;

import java.util.List;

public interface CourseRule {

    public List<ValidationError> validate (Long courseId);
}
