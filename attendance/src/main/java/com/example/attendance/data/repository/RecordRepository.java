package com.example.attendance.data.repository;

import com.example.attendance.data.model.entity.Record;
import com.example.attendance.data.model.entity.RecordPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, RecordPrimaryKey> {

}
