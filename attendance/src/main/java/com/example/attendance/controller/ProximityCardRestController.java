package com.example.attendance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class ProximityCardRestController {

  protected static final Logger logger = LoggerFactory.getLogger(ProximityCardRestController.class);

  // TODO: create new user
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createProximityCard(@RequestBody String proximityCardRequest) {
    return new ResponseEntity<String>("{}", HttpStatus.CREATED);
  }

}
