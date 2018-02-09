package com.example.attendance.data.repository;

import com.example.attendance.data.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
