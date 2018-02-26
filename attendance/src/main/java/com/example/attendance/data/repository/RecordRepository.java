package com.example.attendance.data.repository;

import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.model.entity.Record;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {

  public Collection<Record> findByProximityCard(ProximityCard proximityCard);

}
