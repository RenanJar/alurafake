package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.api.dto.user.InstructorCourseReportDTO;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.domain.error.exception.InvalidUserRoleException;
import br.com.alura.AluraFake.domain.enumeration.Role;
import br.com.alura.AluraFake.domain.user.entity.User;
import br.com.alura.AluraFake.infra.repository.UserRepository;
import br.com.alura.AluraFake.util.report.ReportResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final CourseService courseService;
    private UserRepository userRepository;

    public ReportService(CourseService courseService,UserRepository userRepository) {
        this.courseService = courseService;
        this.userRepository = userRepository;
    }

    public ReportResponse<InstructorCourseReportDTO> generateInstructorCourseReport(Long instructorId) {
        validateInstructorId(instructorId);

        List<InstructorCourseReportDTO> content = courseService.exportInstructorCourseReportData(instructorId);
        Integer totalPublished = courseService.countInstructorCourseReportData(instructorId);

        return new ReportResponse<>(content,totalPublished);
    }


    void validateInstructorId(Long instructorId){
        Optional<User> instructor = userRepository.findById(instructorId);

        if(!instructor.isPresent()){
            throw new EntityNotFoundException("Instructor not found");
        }

        if(!instructor.get().getRole().equals(Role.INSTRUCTOR)){
            throw new InvalidUserRoleException("Insufficient role privileges.");
        }

    }

}
