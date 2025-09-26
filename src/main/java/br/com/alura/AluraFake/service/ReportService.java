package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.api.dto.user.InstructorCourseReportDTO;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.domain.error.exception.InvalidUserRoleException;
import br.com.alura.AluraFake.domain.enumeration.Role;
import br.com.alura.AluraFake.domain.user.entity.User;
import br.com.alura.AluraFake.infra.repository.UserRepository;
import br.com.alura.AluraFake.util.report.ReportResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    private final CourseService courseService;
    private UserRepository userRepository;

    public ReportService(CourseService courseService,UserRepository userRepository) {
        this.courseService = courseService;
        this.userRepository = userRepository;
    }

    public ReportResponse<InstructorCourseReportDTO> generateInstructorCourseReport(Long instructorId) {
        validateInstructorId(instructorId);

        log.info("Starting generating instructor course report from instructorId{}", instructorId);
        List<InstructorCourseReportDTO> content = courseService.exportInstructorCourseReportData(instructorId);

        log.info("Starting generating course count report from instructorId{}", instructorId);
        Integer totalPublished = courseService.countInstructorCourseReportData(instructorId);

        return new ReportResponse<>(content,totalPublished);
    }


    void validateInstructorId(Long instructorId){
        log.info("validation started Instructor Id {}", instructorId);

        Optional<User> instructor = userRepository.findById(instructorId);

        if(!instructor.isPresent()){
            throw new EntityNotFoundException("Instructor not found");
        }

        if(!instructor.get().getRole().equals(Role.INSTRUCTOR)){
            throw new InvalidUserRoleException("Insufficient role privileges.");
        }

        log.info("validation completed");
    }

}
