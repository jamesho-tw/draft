package com.example.attendance.data.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

@Entity
@Table(
    name = "proximity_cards",
    uniqueConstraints = @UniqueConstraint(columnNames = "pin")
)
public class ProximityCard implements Serializable {

  // identification
  // proximity cards and credentials

  private Long id;
  private String pin;
  private String description;
  private boolean enabled;
  private Long creationTime;
  private Long lastModifiedTime;
  private Set<Record> records;

  public ProximityCard() {
  }

  public ProximityCard(String pin, String description, boolean enabled) {
    this(null, pin, description, enabled, null, null);
  }

  public ProximityCard(Long id, String pin, String description, boolean enabled,
      Long creationTime, Long lastModifiedTime) {
    this.id = id;
    this.pin = pin;
    this.description = description;
    this.enabled = enabled;
    this.creationTime = creationTime;
    this.lastModifiedTime = lastModifiedTime;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Column(name = "pin", unique = true, nullable = false, length = 64)
  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  @Type(type = "text")
  @Column(name = "description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Type(type = "org.hibernate.type.NumericBooleanType")
  @Column(name = "is_active", nullable = false)
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @JsonProperty("created")
  @Column(name = "created_unix", nullable = false)
  public Long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Long creationTime) {
    this.creationTime = creationTime;
  }

  @JsonProperty("modified")
  @Column(name = "modified_unix")
  public Long getLastModifiedTime() {
    return lastModifiedTime;
  }

  public void setLastModifiedTime(Long lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  @JsonIgnore
  @OneToMany(mappedBy = "proximityCard", cascade = CascadeType.ALL)
  public Set<Record> getRecords() {
    return records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

}
