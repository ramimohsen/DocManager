package org.docmanager.exception;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.docmanager.exception.custom.AlreadyExistException;
import org.docmanager.exception.custom.NotFoundException;
import org.docmanager.exception.response.ErrorDetails;
import org.docmanager.exception.response.ValidationFailedResponse;
import org.docmanager.exception.response.ViolationErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class DocManagerExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> alreadyExistExceptionHandling(AlreadyExistException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandling(NotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> userNameNotFoundExceptionHandling(UsernameNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> authenticationExceptionHandling(AuthenticationException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now()
                , String.format("Incorrect email or password credentials provided. [ %s ]", exception.getMessage()),
                request.getDescription(false), HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeExceptionHandling(RuntimeException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now()
                , exception.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandling(MethodArgumentNotValidException exception) {

        ValidationFailedResponse error = ValidationFailedResponse
                .builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timeStamp(LocalDateTime.now())
                .build();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {

            error.getViolations().add(ViolationErrors
                    .builder()
                    .fieldName(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build());
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> onConstraintValidationException(ConstraintViolationException e) {

        ValidationFailedResponse error = ValidationFailedResponse
                .builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timeStamp(LocalDateTime.now())
                .build();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {

            error.getViolations().add(ViolationErrors
                    .builder()
                    .fieldName(violation.getPropertyPath().toString())
                    .message(violation.getMessage())
                    .build());
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
