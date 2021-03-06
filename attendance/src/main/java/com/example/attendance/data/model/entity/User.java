package com.example.attendance.data.model.entity;

import com.example.attendance.core.utils.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

@Entity
@Table(
    name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class User implements Serializable {

  private Long id;
  private String username;
  private String password;
  private String avatar;
  private Gender gender;
  private Date birthDate;
  private boolean mustChangePassword;
  private boolean enabled;
  private Long creationTime;
  private Long lastModifiedTime;
  private Set<Role> roles;
  private Set<ProximityCard> proximityCards;
  private Set<Token> tokens;
  private Set<Record> records;

  public User() {
  }

  public User(String username, String password, String avatar, Gender gender, Date birthDate,
      boolean mustChangePassword, boolean enabled) {
    this.username = username;
    this.password = password;
    this.avatar = avatar;
    this.gender = gender;
    this.birthDate = birthDate;
    this.mustChangePassword = mustChangePassword;
    this.enabled = enabled;
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

  @Column(name = "name", unique = true, nullable = false, length = 32)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @JsonIgnore
  @Column(name = "secret", nullable = false, length = 512)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "avatar", length = 2048)
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "gender")
  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  @JsonProperty("birth_date")
  @Temporal(TemporalType.DATE)
  @Column(name = "birth_date")
  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  @JsonIgnore
  @Type(type = "org.hibernate.type.NumericBooleanType")
  @Column(name = "must_change_secret", nullable = false)
  public boolean isMustChangePassword() {
    return mustChangePassword;
  }

  public void setMustChangePassword(boolean mustChangePassword) {
    this.mustChangePassword = mustChangePassword;
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

  @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name = "related_users_roles",
      joinColumns = @JoinColumn(name = "user_id", unique = false),
      inverseJoinColumns = @JoinColumn(name = "role_id", unique = false),
      uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
  )
  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @JsonProperty("cards")
  @ManyToMany(targetEntity = ProximityCard.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name = "related_users_proximity_cards",
      joinColumns = @JoinColumn(name = "user_id", unique = false),
      inverseJoinColumns = @JoinColumn(name = "card_id", unique = false),
      uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "card_id"})
  )
  public Set<ProximityCard> getProximityCards() {
    return proximityCards;
  }

  public void setProximityCards(Set<ProximityCard> proximityCards) {
    this.proximityCards = proximityCards;
  }

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  public Set<Token> getTokens() {
    return tokens;
  }

  public void setTokens(Set<Token> tokens) {
    this.tokens = tokens;
  }

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @OrderBy("loggingTime")
  public Set<Record> getRecords() {
    return records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", avatar='" + avatar + '\'' +
        ", gender=" + gender +
        ", birthDate=" + birthDate +
        ", mustChangePassword=" + mustChangePassword +
        ", enabled=" + enabled +
        ", creationTime=" + creationTime +
        ", lastModifiedTime=" + lastModifiedTime +
        ", roles=" + roles +
        ", proximityCards=" + proximityCards +
        ", tokens=" + tokens +
        ", records=" + records +
        '}';
  }

}
