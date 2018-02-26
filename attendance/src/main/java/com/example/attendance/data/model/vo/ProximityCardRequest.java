package com.example.attendance.data.model.vo;

import java.io.Serializable;

public class ProximityCardRequest implements Serializable {

  private String pin;
  private String description;
  private boolean enabled;

  public ProximityCardRequest() {
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

}
