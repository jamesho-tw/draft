package com.example.attendance.controller;

import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.model.vo.UserRequest;
import com.example.attendance.data.service.UserService;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {

  protected static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

  @Autowired
  private UserService userService;

  // TODO: Create
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
    logger.debug(String.format("UserRequest: %s", userRequest));

    logger.debug(String.format("UserRequest: %s", userRequest.getUsername()));

    return new ResponseEntity<String>("{}", HttpStatus.CREATED);
  }

  // TODO: Retrieve
  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveUserProfile(Authentication authentication) {
    return new ResponseEntity<User>(getUser(authentication), HttpStatus.OK);
  }

  // TODO: Partially Update
  @RequestMapping(value = "", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> update() {
    return new ResponseEntity<String>("{}", HttpStatus.OK);
  }

  // TODO: Delete
  @RequestMapping(value = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> delete() {
    return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
  }

  @ExceptionHandler({ConstraintViolationException.class})
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

  private User getUser(Authentication authentication) {
    if (authentication == null) {
      return null;
    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    if (userDetails == null) {
      return null;
    }
    return userService.loadUserByUsername(userDetails.getUsername());
  }

}
