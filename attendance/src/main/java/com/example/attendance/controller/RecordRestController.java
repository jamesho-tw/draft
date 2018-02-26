package com.example.attendance.controller;

import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.model.entity.Record;
import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.model.vo.RecordRequest;
import com.example.attendance.data.service.ProximityCardService;
import com.example.attendance.data.service.RecordService;
import com.example.attendance.data.service.UserService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/records")
public class RecordRestController {

  protected static final Logger logger = LoggerFactory.getLogger(RecordRestController.class);

  @Autowired
  private ProximityCardService proximityCardService;

  @Autowired
  private UserService userService;

  @Autowired
  private RecordService recordService;

  // create attendance record
  @PreAuthorize("hasAuthority('ROLE_ROBOT')")
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createAttendanceRecord(@RequestBody RecordRequest recordRequest) {
    // TODO: filter logging time

    // check proximity card
    ProximityCard card = proximityCardService.loadProximityCardByPin(recordRequest.getPin());
    if (card == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }

    // load user by proximity card
    User user = userService.loadUserByProximityCard(card);
    if (user == null) {
      return new ResponseEntity<String>("{\"message\":\"User not found\"}", HttpStatus.NOT_FOUND);
    }

    Record record = new Record(user, card, recordRequest.getLoggingTime());

    // save
    record = recordService.createRecord(record);
    if (record == null) {
      return new ResponseEntity<String>("{\"message\":\"error\"}",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Record>(record, HttpStatus.CREATED);
  }

  // retrieve attendance records by user ID
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveAttendanceRecordsByUser(@PathVariable("id") long userId) {
    // TODO: user ID validation

    User user = userService.loadUserById(userId);
    if (user == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }

    Collection<Record> records = user.getRecords();
    if (records == null || records.size() == 0) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Collection<Record>>(records, HttpStatus.OK);
  }

  // retrieve attendance records by proximity card ID
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/cards/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveAttendanceRecordsByProximityCard(
      @PathVariable("id") long cardId) {
    // TODO: proximity card ID validation

    ProximityCard proximityCard = proximityCardService.loadProximityCardById(cardId);
    if (proximityCard == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }

    Collection<Record> records = recordService.loadAttendanceRecordByProximityCard(proximityCard);
    if (records == null || records.size() == 0) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Collection<Record>>(records, HttpStatus.OK);
  }

  // retrieve attendance records by user ID
  // TODO: by logging date
  @PreAuthorize("hasAuthority('ROLE_USER')")
  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveAttendanceRecords(Authentication authentication) {
    User user = getUser(authentication);
    if (user == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }

    Collection<Record> records = user.getRecords();
    if (records == null || records.size() == 0) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Collection<Record>>(records, HttpStatus.OK);
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
