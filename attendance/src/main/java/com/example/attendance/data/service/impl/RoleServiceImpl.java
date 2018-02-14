package com.example.attendance.data.service.impl;

import com.example.attendance.data.model.entity.Role;
import com.example.attendance.data.repository.RoleRepository;
import com.example.attendance.data.service.RoleService;
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

}
