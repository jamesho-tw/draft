package com.example.attendance.data.repository;

import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findByUsername(String username);

  @Query(value = "SELECT u.* FROM "
      + "related_users_proximity_cards r "
      + "JOIN users u ON r.user_id = u.id "
      + "JOIN proximity_cards c ON r.card_id = c.id "
      + "WHERE c.pin = ?1", nativeQuery = true)
  public User findByProximityCard(String ping);

}
