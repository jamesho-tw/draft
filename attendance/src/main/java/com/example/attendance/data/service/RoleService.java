package com.example.attendance.data.service;

import com.example.attendance.data.model.entity.Role;
import java.util.Collection;

public interface RoleService {

  public Role createRole(Role role);

  public Role loadRoleById(long roleId);

  public Role loadRoleByName(String name);

  public Collection<Role> loadAllRoles();

  public Role updateRoleDetails(Role role);

}
