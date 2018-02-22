package com.example.attendance.data.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "identification")
public class Identification implements Serializable {

  private Long id;
  private String pinCode;
  private boolean enabled;
  private Long creationTime;
  private Long lastModifiedTime;

}
