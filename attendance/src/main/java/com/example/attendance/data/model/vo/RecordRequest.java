package com.example.attendance.data.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class RecordRequest implements Serializable {

  private String pin;
  private Long loggingTime;

  public RecordRequest() {
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  @JsonProperty("timestamp")
  public Long getLoggingTime() {
    return loggingTime;
  }

  public void setLoggingTime(Long loggingTime) {
    this.loggingTime = loggingTime;
  }

  @Override
  public String toString() {
    return "RecordRequest{" +
        "pin='" + pin + '\'' +
        ", loggingTime=" + loggingTime +
        '}';
  }

}
