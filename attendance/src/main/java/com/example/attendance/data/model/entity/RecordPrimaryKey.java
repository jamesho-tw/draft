package com.example.attendance.data.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class RecordPrimaryKey implements Serializable {

  private User user;
  private String deviceTag;
  private String deviceType;
  private Long loggingTime;

  public RecordPrimaryKey() {
  }

  public RecordPrimaryKey(User user, String deviceTag, String deviceType, Long loggingTime) {
    this.user = user;
    this.deviceTag = deviceTag;
    this.deviceType = deviceType;
    this.loggingTime = loggingTime;
  }

  @ManyToOne
  @JoinColumn(name = "user_id")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Column(name = "device_tag", length = 32)
  public String getDeviceTag() {
    return deviceTag;
  }

  public void setDeviceTag(String deviceTag) {
    this.deviceTag = deviceTag;
  }

  @Column(name = "device_type", length = 32)
  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  @Column(name = "logged_unix", nullable = false)
  public Long getLoggingTime() {
    return loggingTime;
  }

  public void setLoggingTime(Long loggingTime) {
    this.loggingTime = loggingTime;
  }

}
