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

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorItemDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorItemDTO> errors = ex.getBindingResult().getFieldErrors().stream().map(ErrorItemDTO::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(TaskValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ValidationError>> handleValidationExceptions(TaskValidationException ex) {
        List<ValidationError> errors = ex.getErrors();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        String errorBaseUri = request.getRequestURI();

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Resource not found");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create(errorBaseUri + ProblemType.RESOURCE_NOT_FOUND.getPath()));
        problem.setProperty("path", request.getRequestURI());
        return problem;
    }

    @ExceptionHandler(InvalidUserRoleException.class)
    public ProblemDetail handleEntityNotFound(InvalidUserRoleException ex, HttpServletRequest request) {
        String errorBaseUri = request.getRequestURI();

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Resource not found");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create(errorBaseUri + ProblemType.INVALID_INSTRUCTOR_ROLE.getPath()));
        problem.setProperty("path", request.getRequestURI());
        return problem;
    }
}