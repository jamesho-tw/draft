package com.example.attendance.data.service.impl;

import com.example.attendance.data.model.entity.Role;
import com.example.attendance.data.repository.RoleRepository;
import com.example.attendance.data.service.RoleService;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl implements RoleService {

  protected static final Logger logger = LoggerFactory.getLogger(RoleService.class);

  @Autowired
  private RoleRepository roleRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Role createRole(Role role) {
    if (role == null) {
      // TODO: throw exception
      return null;
    }
    Role entity = role;
    entity.setId(null);

    // timestamp
    entity.setCreationTime(System.currentTimeMillis());

    return roleRepository.saveAndFlush(entity);
  }

  @Override
  public Role loadRoleById(long roleId) {
    if (roleId < 1) {
      // TODO: throw exception
      return null;
    }
    return roleRepository.findOne(roleId);
  }

  @Override
  public Role loadRoleByName(String name) {
    if (!StringUtils.hasLength(StringUtils.trimWhitespace(name))) {
      logger.error(String.format("Invalid name. Name: %s", name));
      // TODO: throw exception
      return null;
    }
    return roleRepository.findByName(name);
  }

  @Override
  public Collection<Role> loadAllRoles() {
    return roleRepository.findAll();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Role updateRoleDetails(Role role) {
    if (role == null || role.getId() == null) {
      // TODO: throw exception
      return null;
    }
    Role entity = roleRepository.findOne(role.getId());
    if (entity == null) {
      // TODO: throw exception
      return null;
    }
    entity.setName(role.getName());
    entity.setDescription(role.getDescription());
    entity.setEnabled(role.isEnabled());
    entity.setLastModifiedTime(System.currentTimeMillis());
    return roleRepository.saveAndFlush(entity);
  }

}
