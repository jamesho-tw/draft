package com.example.attendance.controller;

import com.example.attendance.data.model.entity.ProximityCard;
import com.example.attendance.data.model.entity.Role;
import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.model.vo.UserRequest;
import com.example.attendance.data.service.RoleService;
import com.example.attendance.data.service.UserService;
import java.util.Arrays;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {

  protected static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  // create new user
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
    User user = convert(userRequest);

    // set default role
    if (user.getRoles() == null) {
      Role role = getDefaultRole();
      if (role != null) {
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
      }
    }

    // check if exists
    if (userService.loadUserByUsername(user.getUsername()) != null) {
      return new ResponseEntity<String>("{\"message\":\"'Account already exists\"}",
          HttpStatus.CONFLICT);
    }

    // save
    user = userService.createUser(user);
    if (user == null) {
      return new ResponseEntity<String>("{\"message\":\"error\"}",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<User>(user, HttpStatus.CREATED);
  }

  // retrieve user profile by user ID
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveUserProfile(@PathVariable("id") long userId) {
    // TODO: user ID validation

    User user = userService.loadUserById(userId);
    if (user == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ROLE_USER')")
  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveUserProfile(Authentication authentication) {
    User user = getUser(authentication);
    if (user == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  // partially update user profile
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> updateUserProfile(@PathVariable("id") long userId,
      @RequestBody UserRequest userRequest) {
    // TODO: user ID validation

    User user = convert(userRequest);
    user.setId(userId);
    if (userRequest.getMustChangePassword() != null) {
      user.setMustChangePassword(userRequest.getMustChangePassword());
    }
    if (userRequest.getRoles() != null) {
      user.setRoles(new HashSet<Role>(userRequest.getRoles()));
    }
    if (userRequest.getProximityCards() != null) {
      user.setProximityCards(new HashSet<ProximityCard>(userRequest.getProximityCards()));
    }

    // save
    user = userService.updateUserProfile(user);
    if (user == null) {
      return new ResponseEntity<String>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>("{}", HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ROLE_USER')")
  @RequestMapping(value = "", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> updateUserProfile(Authentication authentication,
      @RequestBody UserRequest userRequest) {
    User user = getUser(authentication);
    if (user == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    long userId = user.getId();
    user = convert(userRequest);
    user.setId(userId);

    // save
    user = userService.updateUserProfile(user);
    if (user == null) {
      return new ResponseEntity<String>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  // TODO: delete user (inactive)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> delete(@PathVariable("id") long userId) {
    User user = userService.loadUserById(userId);
    if (user == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
  }

  private User getUser(Authentication authentication) {
    if (authentication == null) {
      return null;
    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    if (userDetails == null) {
      return null;
    }
    return userService.loadUserByUsername(userDetails.getUsername());
  }

  private Role getDefaultRole() {
    return roleService.loadRoleByName("ROLE_USER");
  }

  private User convert(UserRequest u) {
    if (u == null) {
      return null;
    }
    return new User(u.getUsername(), u.getPassword(), u.getAvatar(), u.getGender(),
        u.getBirthDate(), false, true);
  }

}
