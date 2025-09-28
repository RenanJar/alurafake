package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.api.dto.report.InstructorCourseReportDTO;
import br.com.alura.AluraFake.domain.course.validator.CourseValidator;
import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.infra.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseValidator courseValidator;
    private final CourseRepository courseRepository;

    public CourseService(CourseValidator courseValidator, CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.courseValidator = courseValidator;
    }

    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }

    @Transactional
    public void publishCourse(Long courseId) {

        log.info("starting course validation {}", courseId);
        courseValidator.validate(courseId);
        log.info("Validation Completed {}", courseId);

        log.info("Starting Publication {}", courseId);
        courseRepository.publish(courseId, Status.PUBLISHED, LocalDateTime.now());
        log.info("Publication Completed {}", courseId);
    }

    public boolean verifyStatus(Long courseId, Status status) {
        return courseRepository.existsCourseByIdAndStatus(courseId, status);
    }

    public List<InstructorCourseReportDTO> exportInstructorCourseReportData(Long instructorId) {

        return courseRepository.exportInstructorCourseReportData(instructorId).stream().map(
                projection-> new InstructorCourseReportDTO(
                        projection.getId(),
                        projection.getTitle(),
                        projection.getStatus(),
                        projection.getPublishedAt(),
                        projection.getTaskCount()
                )).toList();
    }

    public Integer countInstructorCourseReportData(Long instructorId){
        return courseRepository.countInstructorCourseReportData(instructorId);
    }

}
