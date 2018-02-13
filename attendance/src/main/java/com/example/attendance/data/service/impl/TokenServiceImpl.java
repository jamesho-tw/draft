package com.example.attendance.data.service.impl;

import com.example.attendance.data.model.entity.Role;
import com.example.attendance.data.model.entity.Token;
import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.repository.TokenRepository;
import com.example.attendance.data.repository.UserRepository;
import com.example.attendance.data.service.TokenService;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service()
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TokenServiceImpl implements TokenService {

  protected static final Logger logger = LoggerFactory.getLogger(TokenService.class);

  // zero or negative for non-expiring tokens
  private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1800; // 3 min

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Token createToken(User user) {
    if (user == null || user.getId() == null) {
      // TODO: throw exception
      return null;
    }
    String tokenKey = extractKey(user);
    Token token = tokenRepository.findOne(tokenKey);
    long now = System.currentTimeMillis();
    if (token != null) {
      token.setCreationTime(now);
      token.setExpirationTime(generateExpirationTime(now));
      return tokenRepository.saveAndFlush(token);
    }
    token = new Token();
    token.setToken(tokenKey);
    token.setCreationTime(now);
    token.setExpirationTime(generateExpirationTime(now));
    token.setUser(user);
    return tokenRepository.save(token);
  }

  @Override
  public Token loadToken(String token) {
    if (!StringUtils.hasLength(StringUtils.trimWhitespace(token))) {
      logger.error(String.format("Invalid token. Token: %s", token));
      // TODO: throw exception
      return null;
    }
    return tokenRepository.findOne(token);
  }

  @Override
  public Token loadTokenByUserId(Long userId) {
    if (userId == null) {
      return null;
    }
    return loadTokenByUser(userRepository.findOne(userId));
  }

  @Override
  public Token loadTokenByUser(User user) {
    if (user == null || user.getId() == null) {
      // TODO: throw exception
      return null;
    }
    return tokenRepository.findByUser(user);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public void revokeToken(Token token) {
    if (token == null) {
      // TODO: throw exception
      return;
    }
    tokenRepository.delete(token);
  }

  // default token generator
  private String extractKey(User user) {
    if (user == null) {
      return null;
    }
    Map<String, String> values = new LinkedHashMap<String, String>();
    if (user.getId() != null) {
      values.put("user_id", String.valueOf(user.getId()));
    }
    values.put("username", user.getUsername());
    if (user.getRoles() != null) {
      List<String> array = null;
      for (Role role : user.getRoles()) {
        if (array == null) {
          array = new ArrayList<String>();
        }
        array.add(role.getName());
      }
      if (array != null) {
        values.put("roles", array.toString());
      }
    }
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("MD5 algorithm not available");
    }
    try {
      byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
      return String.format("%032x", new BigInteger(1, bytes));
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException("UTF-8 encoding not available");
    }
  }

  private long generateExpirationTime(long time) {
    if (time <= 0) {
      return 0;
    }
    return ACCESS_TOKEN_VALIDITY_SECONDS * 1000 + time;
  }

}
