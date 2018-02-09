package com.example.attendance.data.service.impl;

import com.example.attendance.data.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  protected static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.debug(String.format("Username: %s", username));
    com.example.attendance.data.model.entity.User user = userRepository.findOne(1l);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    if (!user.isEnabled()) {
      throw new UsernameNotFoundException("Inactive user");
    }
    UserBuilder builder = User.withUsername(user.getUsername());
    builder.password("");
    builder.accountExpired(true);
    builder.accountLocked(true);
    builder.credentialsExpired(true);
//    String[] authorities = user.getRoles().stream().map(a -> a.getName()).toArray(String[]::new);
    builder.authorities(new String[]{"a"});
    UserDetails u = builder.build();
    logger.info(u.toString());
    return u;
  }

}
