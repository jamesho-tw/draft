package com.example.attendance.data.repository;

import com.example.attendance.data.model.entity.Token;
import com.example.attendance.data.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {

  public Token findByUser(User user);

}
