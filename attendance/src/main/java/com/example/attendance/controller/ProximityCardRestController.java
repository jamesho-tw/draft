package com.example.attendance.controller;

import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.model.vo.ProximityCardRequest;
import com.example.attendance.data.service.ProximityCardService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class ProximityCardRestController {

  protected static final Logger logger = LoggerFactory.getLogger(ProximityCardRestController.class);

  @Autowired
  private ProximityCardService proximityCardService;

  // create new user
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createProximityCard(
      @RequestBody ProximityCardRequest proximityCardRequest) {
    ProximityCard proximityCard = convert(proximityCardRequest);

    // check if exists
    if (proximityCardService.loadProximityCardByPin(proximityCard.getPin()) != null) {
      return new ResponseEntity<String>("{\"message\":\"'Proximity card already exists\"}",
          HttpStatus.CONFLICT);
    }

    // save
    proximityCard = proximityCardService.createProximityCard(proximityCard);
    if (proximityCard == null) {
      return new ResponseEntity<String>("{\"message\":\"error\"}",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<ProximityCard>(proximityCard, HttpStatus.CREATED);
  }

  // retrieve proximity card
  // TODO: by pagination
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveProximityCard() {
    Collection<ProximityCard> cards = proximityCardService.loadAllProximityCard();
    if (cards == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Collection<ProximityCard>>(cards, HttpStatus.OK);
  }

  // retrieve proximity card by card ID
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveProximityCard(@PathVariable("id") long cardId) {
    // TODO: card ID validation

    ProximityCard proximityCard = proximityCardService.loadProximityCardById(cardId);
    if (proximityCard == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<ProximityCard>(proximityCard, HttpStatus.OK);
  }

  // partially update proximity card
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> updateProximityCard(@PathVariable("id") long cardId,
      @RequestBody ProximityCardRequest proximityCardRequest) {
    // TODO: card ID validation

    ProximityCard proximityCard = convert(proximityCardRequest);
    proximityCard.setId(cardId);

    // save
    proximityCard = proximityCardService.updateUserProfile(proximityCard);
    if (proximityCard == null) {
      return new ResponseEntity<String>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>("{}", HttpStatus.OK);
  }

  // TODO: delete proximity card (inactive)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") long cardId) {
    // TODO: card ID validation

    ProximityCard card = proximityCardService.loadProximityCardById(cardId);
    if (card == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
  }

  private ProximityCard convert(ProximityCardRequest card) {
    if (card == null) {
      return null;
    }
    return new ProximityCard(card.getPin(), card.getDescription(), card.isEnabled());
  }

}
