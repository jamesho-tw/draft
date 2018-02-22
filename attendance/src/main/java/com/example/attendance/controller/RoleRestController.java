package com.example.attendance.controller;

import com.example.attendance.data.model.entity.Role;
import com.example.attendance.data.service.RoleService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleRestController {

  protected static final Logger logger = LoggerFactory.getLogger(RoleRestController.class);

  @Autowired
  private RoleService roleService;

  // create new role
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createRole(@RequestBody Role roleRequest) {
    Role role = convert(roleRequest);

    // check if exists
    if (roleService.loadRoleByName(role.getName()) != null) {
      return new ResponseEntity<String>("{\"message\":\"'Role already exists\"}",
          HttpStatus.CONFLICT);
    }

    // save
    role = roleService.createRole(role);
    if (role == null) {
      return new ResponseEntity<String>("{\"message\":\"error\"}",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Role>(role, HttpStatus.CREATED);
  }

  // retrieve roles
  // TODO: by pagination
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveRoleDetails() {
    Collection<Role> roles = roleService.loadAllRoles();
    if (roles == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Collection<Role>>(roles, HttpStatus.OK);
  }

  // retrieve role details by role ID
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> retrieveRoleDetails(@PathVariable long roleId) {
    Role role = roleService.loadRoleById(roleId);
    if (role == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<Role>(role, HttpStatus.OK);
  }

  // partially update role details
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> updateRoleDetails(@PathVariable long roleId,
      @RequestBody Role roleRequest) {
    // TODO: role ID validation

    Role role = convert(roleRequest);
    role.setId(roleId);

    // save
    role = roleService.updateRoleDetails(role);
    if (role == null) {
      return new ResponseEntity<String>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>("{}", HttpStatus.OK);
  }

  // TODO: delete role (inactive)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> delete(@PathVariable long roleId) {
    Role role = roleService.loadRoleById(roleId);
    if (role == null) {
      return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
  }

  private Role convert(Role r) {
    if (r == null) {
      return null;
    }
    String name = StringUtils.trimWhitespace(r.getName());
    String description = StringUtils.trimWhitespace(r.getDescription());
    return new Role(name, description);
  }

}
