package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.api.dto.user.InstructorCourseReportDTO;
import br.com.alura.AluraFake.domain.course.validator.CourseValidator;
import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.infra.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService {

    private final CourseValidator courseValidator;
    private final CourseRepository courseRepository;

    public CourseService(CourseValidator courseValidator, CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.courseValidator = courseValidator;
    }

    public boolean existsById(Long id) {
        return courseRepository.existsById(id.intValue());
    }

    @Transactional
    public void publishCourse(Long courseId) {
        courseValidator.validate(courseId);
        courseRepository.publish(courseId, Status.PUBLISHED, LocalDateTime.now());
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
