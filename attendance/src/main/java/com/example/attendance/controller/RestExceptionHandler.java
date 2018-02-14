package com.example.attendance.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

  protected static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
    logger.debug(String.format("%s", e.toString()));
    for (ConstraintViolation<?> c : e.getConstraintViolations()) {
      logger.debug(String.format("%s >> %s", c.getPropertyPath(), c.getMessage()));
    }
    // TODO: exception response value object model
    // errorCode
    // errorMessage
    return new ResponseEntity<String>("{\"message\":\"Invalid argument\"}", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataIntegrityViolationException(
      DataIntegrityViolationException e) {
    logger.debug(e.getMessage());
    logger.debug(e.toString());
    return new ResponseEntity<String>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
