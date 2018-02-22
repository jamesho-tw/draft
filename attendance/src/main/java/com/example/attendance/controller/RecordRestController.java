package com.example.attendance.controller;

import com.example.attendance.data.model.vo.RecordRequest;
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
@RequestMapping("/records")
public class RecordRestController {

  protected static final Logger logger = LoggerFactory.getLogger(RecordRestController.class);

  // create attendance record
  @PreAuthorize("hasAuthority('ROLE_ROBOT')")
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createAttendanceRecord(@RequestBody RecordRequest recordRequest) {
    logger.debug(String.format("%s", recordRequest));
    return new ResponseEntity<String>("", HttpStatus.CREATED);
  }

}
