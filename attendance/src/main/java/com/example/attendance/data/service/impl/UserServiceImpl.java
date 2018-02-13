package com.example.attendance.data.service.impl;

import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.repository.UserRepository;
import com.example.attendance.data.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service()
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

  protected static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  public User loadUserByUsername(String username) {
    if (!StringUtils.hasLength(StringUtils.trimWhitespace(username))) {
      logger.error(String.format("Invalid username. Username: %s", username));
      // TODO: throw exception
      return null;
    }
    return userRepository.findByUsername(username);
  }

}
