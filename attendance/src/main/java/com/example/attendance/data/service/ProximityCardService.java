package com.example.attendance.data.service;

import com.example.attendance.data.model.entity.ProximityCard;
import java.util.Collection;

public interface ProximityCardService {

  public ProximityCard createProximityCard(ProximityCard proximityCard);

  public ProximityCard loadProximityCardById(long cardId);

  public ProximityCard loadProximityCardByPin(String pin);

  public Collection<ProximityCard> loadAllProximityCard();

  public ProximityCard updateUserProfile(ProximityCard proximityCard);

}
