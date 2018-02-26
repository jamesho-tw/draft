package com.example.attendance.data.service;

import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.model.entity.Record;
import com.example.attendance.data.model.entity.User;
import java.util.Collection;

public interface RecordService {

  public Record createRecord(Record record);

  public Record loadAttendanceRecordById(long record_id);

  public Collection<Record> loadAttendanceRecordByProximityCard(ProximityCard proximityCard);

}
