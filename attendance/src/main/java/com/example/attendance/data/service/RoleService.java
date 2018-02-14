package com.example.attendance.data.service;

import com.example.attendance.data.model.entity.Role;

public interface RoleService {

  public Role loadRoleById(long roleId);

  public Role loadRoleByName(String name);

}
