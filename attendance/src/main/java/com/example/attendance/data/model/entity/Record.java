package com.example.attendance.data.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@IdClass(RecordPrimaryKey.class)
@Table(
    name = "attendance_records",
    uniqueConstraints = @UniqueConstraint(columnNames = {
        "device_tag",
        "device_type",
        "logged_unix"
    })
)
public class Record implements Serializable {

  private User user;
  private String deviceTag;
  private String deviceType;
  private Long loggingTime;
  private Long creationTime;

  public Record() {
  }

  @Id
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Id
  public String getDeviceTag() {
    return deviceTag;
  }

  public void setDeviceTag(String deviceTag) {
    this.deviceTag = deviceTag;
  }

  @Id
  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  @Id
  public Long getLoggingTime() {
    return loggingTime;
  }

  public void setLoggingTime(Long loggingTime) {
    this.loggingTime = loggingTime;
  }

  @Column(name = "created_unix", nullable = false)
  public Long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Long creationTime) {
    this.creationTime = creationTime;
  }

}
