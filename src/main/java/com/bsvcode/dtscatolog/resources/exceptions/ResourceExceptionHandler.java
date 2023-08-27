package com.bsvcode.dtscatolog.resources.exceptions;

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
    error.setTimestamp(Instant.now());
    error.setStatus(HttpStatus.NOT_FOUND.value());
    error.setError("Resource not found");
    error.setMessage(e.getMessage());
    error.setPath(request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
