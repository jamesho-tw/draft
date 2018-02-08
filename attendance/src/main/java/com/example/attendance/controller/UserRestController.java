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
@RequestMapping("/users")
public class UserRestController {

  protected static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

  // TODO: Create
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> create(@RequestBody String user) {
    return new ResponseEntity<String>("{}", HttpStatus.CREATED);
  }

  // TODO: Retrieve
  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieve() {
    return new ResponseEntity<String>("{}", HttpStatus.OK);
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

}
