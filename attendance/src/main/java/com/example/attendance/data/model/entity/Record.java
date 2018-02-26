package com.example.attendance.data.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
    name = "attendance_records",
    uniqueConstraints = @UniqueConstraint(columnNames = {
        "user_id",
        "card_id",
        "logged_unix"
    })
)
public class Record implements Serializable {

  private Long id;
  private User user;
  private ProximityCard proximityCard;
  private Long loggingTime;
  private Long creationTime;

  public Record() {
  }

  public Record(User user, ProximityCard proximityCard, Long loggingTime) {
    this(user, proximityCard, loggingTime, null);
  }

  public Record(User user, ProximityCard proximityCard, Long loggingTime, Long creationTime) {
    this.user = user;
    this.proximityCard = proximityCard;
    this.loggingTime = loggingTime;
    this.creationTime = creationTime;
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

  @JsonProperty("user_id")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  @JsonIdentityReference(alwaysAsId = true)
  @ManyToOne
  @JoinColumn(name = "user_id")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @JsonProperty("card")
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "pin")
  @JsonIdentityReference(alwaysAsId = true)
  @ManyToOne
  @JoinColumn(name = "card_id")
  public ProximityCard getProximityCard() {
    return proximityCard;
  }

  public void setProximityCard(ProximityCard proximityCard) {
    this.proximityCard = proximityCard;
  }

  @JsonProperty("logged")
  @Column(name = "logged_unix", nullable = false)
  public Long getLoggingTime() {
    return loggingTime;
  }

  public void setLoggingTime(Long loggingTime) {
    this.loggingTime = loggingTime;
  }

  @JsonProperty("created")
  @Column(name = "created_unix", nullable = false)
  public Long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Long creationTime) {
    this.creationTime = creationTime;
  }

  @Override
  public String toString() {
    return "Record{" +
        "user=" + user +
        ", proximityCard=" + proximityCard +
        ", loggingTime=" + loggingTime +
        ", creationTime=" + creationTime +
        '}';
  }

}
