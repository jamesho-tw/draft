package com.example.attendance.data.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class RecordRequest implements Serializable {

  private String pinCode;
  private String deviceTag;
  private String deviceType;
  private Long loggingTime;

  public RecordRequest() {
  }

  @JsonProperty("pin")
  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }

  @JsonProperty("tag")
  public String getDeviceTag() {
    return deviceTag;
  }

  public void setDeviceTag(String deviceTag) {
    this.deviceTag = deviceTag;
  }

  @JsonProperty("type")
  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
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
        "pinCode='" + pinCode + '\'' +
        ", deviceTag='" + deviceTag + '\'' +
        ", deviceType='" + deviceType + '\'' +
        ", loggingTime=" + loggingTime +
        '}';
  }

}
