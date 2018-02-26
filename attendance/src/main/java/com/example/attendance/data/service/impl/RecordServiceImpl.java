package com.example.attendance.data.service.impl;

import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.model.entity.Record;
import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.repository.RecordRepository;
import com.example.attendance.data.service.RecordService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RecordServiceImpl implements RecordService {

  protected static final Logger logger = LoggerFactory.getLogger(RecordService.class);

  @Autowired
  private RecordRepository recordRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Record createRecord(Record record) {
    if (record == null) {
      // TODO: throw exception
      return null;
    }
    Record entity = record;

    // timestamp
    entity.setCreationTime(System.currentTimeMillis());

    return recordRepository.saveAndFlush(record);
  }

  @Override
  public Record loadAttendanceRecordById(long record_id) {
    return recordRepository.findOne(record_id);
  }

  @Override
  public Collection<Record> loadAttendanceRecordByProximityCard(ProximityCard proximityCard) {
    if (proximityCard == null) {
      return null;
    }
    return recordRepository.findByProximityCard(proximityCard);
  }

}
