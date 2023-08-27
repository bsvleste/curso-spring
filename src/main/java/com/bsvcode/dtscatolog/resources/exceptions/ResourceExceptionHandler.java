package com.bsvcode.dtscatolog.resources.exceptions;

import com.bsvcode.dtscatolog.services.exceptions.DatabaseResourceNotFoundException;
import com.bsvcode.dtscatolog.services.exceptions.ResourceNotFoundException;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandarError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    StandarError error = new StandarError();
    HttpStatus status = HttpStatus.NOT_FOUND;
    error.setTimestamp(Instant.now());
    error.setStatus(status.value());
    error.setError("Resource not found");
    error.setMessage(e.getMessage());
    error.setPath(request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(DatabaseResourceNotFoundException.class)
  public ResponseEntity<StandarError> database(DatabaseResourceNotFoundException e, HttpServletRequest request) {
    StandarError error = new StandarError();
    HttpStatus status = HttpStatus.BAD_REQUEST;
    error.setTimestamp(Instant.now());
    error.setStatus(status.value());
    error.setError("Database exception");
    error.setMessage(e.getMessage());
    error.setPath(request.getRequestURI());
    return ResponseEntity.status(status).body(error);
  }
}
