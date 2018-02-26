package com.example.attendance.data.repository;

import com.example.attendance.data.model.entity.ProximityCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProximityCardRepository extends JpaRepository<ProximityCard, Long> {

  public ProximityCard findByPin(String pin);

}
