package com.example.attendance.data.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Type;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Role implements Serializable {

  private Long id;
  private String name;
  private String description;
  private boolean enabled;
  private Long creationTime;
  private Long lastModifiedTime;

  public Role() {
  }

  public Role(String name) {
    this(null, name, null, false, null, null);
  }

  public Role(String name, String description) {
    this(null, name, description, true, null, null);
  }

  public Role(String name, String description, boolean enabled) {
    this(null, name, description, enabled, null, null);
  }

  public Role(Long id, String name, String description, boolean enabled, Long creationTime,
      Long lastModifiedTime) {
    this.id = id;
    this.name = name;
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

  @Column(name = "name", unique = true, nullable = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  @Column(name = "created_unix", nullable = false)
  public Long getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Long creationTime) {
    this.creationTime = creationTime;
  }

  @Column(name = "modified_unix")
  public Long getLastModifiedTime() {
    return lastModifiedTime;
  }

  public void setLastModifiedTime(Long lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  @Override
  public String toString() {
    return "Role{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", enabled=" + enabled +
        ", creationTime=" + creationTime +
        ", lastModifiedTime=" + lastModifiedTime +
        '}';
  }

}
