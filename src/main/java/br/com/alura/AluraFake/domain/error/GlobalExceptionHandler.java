package br.com.alura.AluraFake.domain.error;

import br.com.alura.AluraFake.domain.error.dto.ErrorItemDTO;
import br.com.alura.AluraFake.domain.error.dto.ValidationError;
import br.com.alura.AluraFake.domain.error.exception.EntityNotFoundException;
import br.com.alura.AluraFake.domain.error.exception.InvalidUserRoleException;
import br.com.alura.AluraFake.domain.error.exception.TaskValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorItemDTO> errors = ex.getBindingResult().getFieldErrors().stream().map(ErrorItemDTO::new).toList();

        String errorBaseUri = request.getRequestURI();

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ProblemType.VALIDATION_ERROR.getTitle());
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create(errorBaseUri + ProblemType.VALIDATION_ERROR.getPath()));
        problem.setProperty("path", request.getRequestURI());
        problem.setProperties(Map.of("errors",errors));
        return problem;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        String errorBaseUri = request.getRequestURI();

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle(ProblemType.RESOURCE_NOT_FOUND.getTitle());
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create(errorBaseUri + ProblemType.RESOURCE_NOT_FOUND.getPath()));
        problem.setProperty("path", request.getRequestURI());
        return problem;
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    public ProblemDetail handleEntityNotFound(InvalidUserRoleException ex, HttpServletRequest request) {
        String errorBaseUri = request.getRequestURI();

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ProblemType.INVALID_INSTRUCTOR_ROLE.getTitle());
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create(errorBaseUri + ProblemType.INVALID_INSTRUCTOR_ROLE.getPath()));
        problem.setProperty("path", request.getRequestURI());
        return problem;
    }

    @ExceptionHandler(TaskValidationException.class)
    public ProblemDetail handleEntityNotFound(TaskValidationException ex, HttpServletRequest request) {
        String errorBaseUri = request.getRequestURI();

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(ProblemType.VALIDATION_ERROR.getTitle());
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create(errorBaseUri + ProblemType.VALIDATION_ERROR.getPath()));
        problem.setProperty("path", request.getRequestURI());
        problem.setProperties(Map.of("errors",ex.getErrors()));
        return problem;
    }
}