package br.com.alura.AluraFake.service;

import br.com.alura.AluraFake.domain.enumeration.Role;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.domain.error.exception.InvalidUserRoleException;
import br.com.alura.AluraFake.domain.user.entity.User;
import br.com.alura.AluraFake.infra.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportServiceTest {

    private CourseService courseService;
    private UserRepository userRepository;
    private ReportService reportService;

    @BeforeEach
    void setup() {
        courseService = mock(CourseService.class);
        userRepository = mock(UserRepository.class);
        reportService = new ReportService(courseService, userRepository);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenInstructorDoesNotExist() {
        Long instructorId = 1L;

        when(userRepository.findById(instructorId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> reportService.generateInstructorCourseReport(instructorId));

        assertEquals("Instructor not found", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidUserRoleException_whenUserIsNotInstructor() {
        Long instructorId = 2L;
        User user = new User("Alice", "alice@email.com", Role.STUDENT); // Role diferente
        when(userRepository.findById(instructorId)).thenReturn(Optional.of(user));

        InvalidUserRoleException exception = assertThrows(InvalidUserRoleException.class,
                () -> reportService.generateInstructorCourseReport(instructorId));

        assertEquals("Insufficient role privileges.", exception.getMessage());
    }
}