package com.bsvcode.dtscatolog.resources.exceptions;

import com.bsvcode.dtscatolog.services.exceptions.DatabaseResourceNotFoundException;
import com.bsvcode.dtscatolog.services.exceptions.ResourceNotFoundException;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandarError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    StandarError error = new StandarError();
    error.setTimestamp(Instant.now());
    error.setStatus(status.value());
    error.setError("Resource not found");
    error.setMessage(e.getMessage());
    error.setPath(request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(DatabaseResourceNotFoundException.class)
  public ResponseEntity<StandarError> database(DatabaseResourceNotFoundException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    StandarError error = new StandarError();
    error.setTimestamp(Instant.now());
    error.setStatus(status.value());
    error.setError("Database exception");
    error.setMessage(e.getMessage());
    error.setPath(request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
    ValidationError error = new ValidationError();
    error.setTimestamp(Instant.now());
    error.setStatus(status.value());
    error.setError("Validation exception");
    error.setMessage(e.getMessage());
    error.setPath(request.getRequestURI());

    for (FieldError errors : e.getBindingResult().getFieldErrors()) {
      error.addError(errors.getField(), errors.getDefaultMessage());
    }

    return ResponseEntity.status(status).body(error);
  }
}
