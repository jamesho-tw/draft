package com.example.attendance.data.service.impl;

import com.example.attendance.data.model.entity.Role;
import com.example.attendance.data.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    com.example.attendance.data.model.entity.User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    if (!user.isEnabled()) {
      throw new UsernameNotFoundException("Inactive user");
    }
    UserBuilder builder = User.withUsername(user.getUsername());
    builder.password(user.getPassword());
    builder.accountExpired(false);
    builder.accountLocked(false);
    builder.credentialsExpired(false);
    builder.authorities(convert(user.getRoles()));
    return builder.build();
  }

  private List<? extends GrantedAuthority> convert(Collection<Role> roles) {
    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (Role role : roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }
    return (List<? extends GrantedAuthority>) authorities;
  }

}
