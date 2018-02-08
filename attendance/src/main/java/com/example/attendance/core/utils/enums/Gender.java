package com.example.attendance.core.utils.enums;

public enum Gender {

  FEMALE(0),
  MALE(1);

  private int code;

  private Gender(int code) {
    this.code = code;
  }

  public static Gender valueOf(int code) {
    for (Gender e : Gender.values()) {
      if (e.code == code) {
        return e;
      }
    }
    return null;
  }

  public int getCode() {
    return code;
  }

}
