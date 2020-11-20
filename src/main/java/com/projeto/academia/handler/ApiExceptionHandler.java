package com.projeto.academia.handler;

import com.projeto.academia.exception.UserAlreadyExistsException;
import com.projeto.academia.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handlerUserNotFoundException(UserNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.ENTITY_NOT_FOUND;
        String detail = ex.getMessage();

        ProblemDetails body = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handlerUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_ALREADY_EXISTS;
        String detail = ex.getMessage();

        ProblemDetails body = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

            if (body == null) {

                body = ProblemDetails.builder()
                        .title(status.getReasonPhrase())
                        .status(status.value())
                        .build();

            } else if (body instanceof String) {

                body = ProblemDetails.builder()
                        .title((String) body)
                        .status(status.value())
                        .build();
            }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ProblemDetails.ProblemDetailsBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {

        return ProblemDetails.builder()
                .status(status.value())
                .type(problemType.getUrl())
                .title(problemType.getTitle())
                .detail(detail)
                .timesTamp(LocalDateTime.now());
    }
}
