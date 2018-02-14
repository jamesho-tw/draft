package com.example.attendance.data.service;

import com.example.attendance.data.model.entity.User;

public interface UserService {

  public User createUser(User user);

  public User loadUserById(long userId);

  public User loadUserByUsername(String username);

  public User updateUserProfile(User user);

  public void updatePassword(long userId, String password);

}
