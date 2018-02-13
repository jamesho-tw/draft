package com.example.attendance.data.service;

import com.example.attendance.data.model.entity.User;

public interface UserService {

  public User loadUserByUsername(String username);

}
