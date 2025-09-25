package br.com.alura.AluraFake.infra.repository.projections;

import br.com.alura.AluraFake.domain.enumeration.Status;

import java.time.LocalDateTime;

public interface InstructorCourseReportProjection {

    Long getId();
    String getTitle();
    Status getStatus();
    LocalDateTime getPublishedAt();
    Integer getTaskCount();

}
