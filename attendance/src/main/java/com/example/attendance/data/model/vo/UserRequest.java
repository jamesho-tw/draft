package com.example.attendance.data.model.vo;

import com.example.attendance.core.utils.enums.Gender;
import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;

public class UserRequest implements Serializable {

  private String username;
  private String password;
  private String avatar;
  private Gender gender;
  private Date birthDate;
  private Boolean mustChangePassword;
  private boolean enabled;
  private Collection<Role> roles;
  private Collection<ProximityCard> proximityCards;

  @NotBlank
  @Min(2)
  @Max(32)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @NotBlank
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  @JsonProperty("birth_date")
  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  @JsonProperty("must_change_password")
  public Boolean getMustChangePassword() {
    return mustChangePassword;
  }

  public void setMustChangePassword(Boolean mustChangePassword) {
    this.mustChangePassword = mustChangePassword;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

  @JsonProperty("cards")
  public Collection<ProximityCard> getProximityCards() {
    return proximityCards;
  }

  public void setProximityCards(Collection<ProximityCard> proximityCards) {
    this.proximityCards = proximityCards;
  }

}
