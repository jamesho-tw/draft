package com.example.attendance.data.service.impl;

import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.repository.ProximityCardRepository;
import com.example.attendance.data.service.ProximityCardService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProximityCardServiceImpl implements ProximityCardService {

  protected static final Logger logger = LoggerFactory.getLogger(ProximityCardService.class);

  @Autowired
  private ProximityCardRepository proximityCardRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public ProximityCard createProximityCard(ProximityCard proximityCard) {
    if (proximityCard == null) {
      // TODO: throw exception
      return null;
    }
    ProximityCard entity = proximityCard;
    entity.setId(null);

    // timestamp
    entity.setCreationTime(System.currentTimeMillis());

    return proximityCardRepository.saveAndFlush(entity);
  }

  @Override
  public ProximityCard loadProximityCardById(long cardId) {
    if (cardId < 1) {
      // TODO: throw exception
      return null;
    }
    return proximityCardRepository.findOne(cardId);
  }

  @Override
  public ProximityCard loadProximityCardByPin(String pin) {
    if (!StringUtils.hasLength(StringUtils.trimWhitespace(pin))) {
      logger.error(String.format("Invalid pin. Pin: %s", pin));
      // TODO: throw exception
      return null;
    }
    return proximityCardRepository.findByPin(pin);
  }

  @Override
  public Collection<ProximityCard> loadAllProximityCard() {
    return proximityCardRepository.findAll();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public ProximityCard updateUserProfile(ProximityCard proximityCard) {
    if (proximityCard == null || proximityCard.getId() == null) {
      // TODO: throw exception
      return null;
    }
    ProximityCard entity = proximityCardRepository.findOne(proximityCard.getId());
    if (entity == null) {
      // TODO: throw exception
      return null;
    }
    entity.setDescription(proximityCard.getDescription());
    entity.setEnabled(proximityCard.isEnabled());
    entity.setLastModifiedTime(System.currentTimeMillis());
    return proximityCardRepository.saveAndFlush(entity);
  }

}
