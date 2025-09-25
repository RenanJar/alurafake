package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.domain.enumeration.Status;
import br.com.alura.AluraFake.domain.course.validator.CourseValidator;
import br.com.alura.AluraFake.infra.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CourseService {

    private final CourseValidator courseValidator;
    private final CourseRepository courseRepository;

    public CourseService(CourseValidator courseValidator,CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.courseValidator = courseValidator;
    }

    public boolean existsById(Long id) {
        return courseRepository.existsById(id.intValue());
    }

    @Transactional
    public void publishCourse(Long courseId) {
        courseValidator.validate(courseId);
        courseRepository.publish(courseId,Status.PUBLISHED, LocalDateTime.now());
    }

    public boolean verifyStatus(Long courseId, Status status) {
        return courseRepository.existsCourseByIdAndStatus(courseId,status);
    }

}
