package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.api.dto.report.InstructorCourseReportDTO;
import br.com.alura.AluraFake.domain.course.validator.CourseValidator;
import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.infra.repository.CourseRepository;
import br.com.alura.AluraFake.infra.repository.projections.InstructorCourseReportProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    private CourseValidator courseValidator;
    private CourseRepository courseRepository;
    private CourseService courseService;

    record InstructorCourseReportProjectionStub(
            Long id,
            String title,
            Status status,
            LocalDateTime publishedAt,
            Integer taskCount
    ) implements InstructorCourseReportProjection {
        @Override public Long getId() { return id; }
        @Override public String getTitle() { return title; }
        @Override public Status getStatus() { return status; }
        @Override public LocalDateTime getPublishedAt() { return publishedAt; }
        @Override public Integer getTaskCount() { return taskCount; }
    }

    @BeforeEach
    void setUp() {
        courseValidator = mock(CourseValidator.class);
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseService(courseValidator, courseRepository);
    }

    @Test
    void shouldReturnTrueWhenCourseExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        boolean exists = courseService.existsById(1L);

        assertTrue(exists);
        verify(courseRepository).existsById(1L);
    }

    @Test
    void shouldValidateAndPublishCourse() {
        Long courseId = 10L;

        courseService.publishCourse(courseId);

        verify(courseValidator).validate(courseId);
        verify(courseRepository).publish(eq(courseId), eq(Status.PUBLISHED), any(LocalDateTime.class));
    }

    @Test
    void shouldReturnTrueWhenCourseHasGivenStatus() {
        Long courseId = 5L;
        Status status = Status.PUBLISHED;
        when(courseRepository.existsCourseByIdAndStatus(courseId, status)).thenReturn(true);

        boolean result = courseService.verifyStatus(courseId, status);

        assertTrue(result);
        verify(courseRepository).existsCourseByIdAndStatus(courseId, status);
    }

    @Test
    void shouldExportInstructorCourseReportData() {
        Long instructorId = 1L;

        var projection = new InstructorCourseReportProjectionStub(
                100L,
                "Spring Boot",
                Status.PUBLISHED,
                LocalDateTime.of(2025, 1, 1, 10, 0),
                3
        );

        when(courseRepository.exportInstructorCourseReportData(instructorId))
                .thenReturn(List.of(projection));

        List<InstructorCourseReportDTO> result = courseService.exportInstructorCourseReportData(instructorId);

        assertEquals(1, result.size());
        InstructorCourseReportDTO dto = result.get(0);
        assertEquals(100L, dto.getId());
        assertEquals("Spring Boot", dto.getTitle());
        assertEquals(Status.PUBLISHED, dto.getStatus());
        assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), dto.getPublishedAt());
        assertEquals(3, dto.getTaskCount());

        verify(courseRepository).exportInstructorCourseReportData(instructorId);
    }

    @Test
    void shouldCountInstructorCourseReportData() {
        Long instructorId = 2L;
        when(courseRepository.countInstructorCourseReportData(instructorId)).thenReturn(42);

        Integer result = courseService.countInstructorCourseReportData(instructorId);

        assertEquals(42, result);
        verify(courseRepository).countInstructorCourseReportData(instructorId);
    }
}