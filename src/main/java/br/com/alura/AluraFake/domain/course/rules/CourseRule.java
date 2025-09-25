package br.com.alura.AluraFake.domain.course.rules;

import br.com.alura.AluraFake.domain.error.dto.ValidationError;

import java.util.List;

public interface CourseRule {

    public List<ValidationError> validate (Long courseId);
}
