package com.example.attendance.data.service.impl;

import com.example.attendance.core.utils.enums.Gender;
import com.example.attendance.data.model.entity.Reader;
import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.repository.UserRepository;
import com.example.attendance.data.service.UserService;
import java.util.Date;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

  protected static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public User createUser(User user) {
    if (user == null) {
      // TODO: throw exception
      return null;
    }
    User entity = user;
    entity.setId(null);

    // TOOD: validation password
    entity.setPassword(passwordEncoder.encode(user.getPassword()));

    // TODO: default role
    // entity.setRoles();

    // timestamp
    entity.setCreationTime(System.currentTimeMillis());

    return userRepository.saveAndFlush(entity);
  }

  @Override
  public User loadUserById(long userId) {
    if (userId < 1) {
      // TODO: throw exception
      return null;
    }
    return userRepository.findOne(userId);
  }

  @Override
  public User loadUserByUsername(String username) {
    if (!StringUtils.hasLength(StringUtils.trimWhitespace(username))) {
      logger.error(String.format("Invalid username. Username: %s", username));
      // TODO: throw exception
      return null;
    }
    return userRepository.findByUsername(username);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public User updateUserProfile(User user) {
    if (user == null || user.getId() == null) {
      // TODO: throw exception
      return null;
    }
    User entity = userRepository.findOne(user.getId());
    if (entity == null) {
      // TODO: throw exception
      return null;
    }
    entity.setAvatar(user.getAvatar());
    entity.setGender(user.getGender());
    entity.setBirthDate(user.getBirthDate());
    entity.setMustChangePassword(user.isMustChangePassword());
    entity.setEnabled(user.isEnabled());
    entity.setLastModifiedTime(System.currentTimeMillis());
    entity.setRoles(user.getRoles());
    Set<Reader> readers = user.getReaders();
    if (readers != null) {
      for (Reader reader : readers) {
        if (reader.getCreationTime() == null) {
          reader.setCreationTime(System.currentTimeMillis());
        } else {
          reader.setLastModifiedTime(System.currentTimeMillis());
        }
      }
    }
    entity.setReaders(readers);
    logger.debug(String.format("%s", entity));
    return userRepository.saveAndFlush(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void updatePassword(long userId, String password) {
    if (userId < 1) {
      // TODO: throw exception
      return;
    }
    User entity = userRepository.findOne(userId);
    if (entity == null) {
      // TODO: throw exception
      return;
    }

    // TOOD: validation password
    entity.setPassword(passwordEncoder.encode(password));

    // timestamp
    entity.setLastModifiedTime(System.currentTimeMillis());

    // XXX: should returns
    userRepository.saveAndFlush(entity);
    return;
  }

}
