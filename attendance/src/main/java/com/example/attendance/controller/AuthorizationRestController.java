package com.example.attendance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationRestController {

  protected static final Logger logger = LoggerFactory.getLogger(AuthorizationRestController.class);

  // TODO: Create
  @RequestMapping(value = "/token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createToken() {
    return new ResponseEntity<String>("{}", HttpStatus.CREATED);
  }

}
