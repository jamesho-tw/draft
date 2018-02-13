package com.example.attendance.data.service;

import com.example.attendance.data.model.entity.Token;
import com.example.attendance.data.model.entity.User;

public interface TokenService {

  public Token createToken(User user);

  public Token loadToken(String token);

  public Token loadTokenByUserId(Long userId);

  public Token loadTokenByUser(User user);

  public void revokeToken(Token token);

}
