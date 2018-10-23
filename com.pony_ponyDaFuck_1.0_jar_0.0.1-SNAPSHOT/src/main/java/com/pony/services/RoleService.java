package com.pony.services;

import java.util.List;

import com.pony.models.Role;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long roleId);

    Role findByName(String roleName);
  
    Role insert(Role role);

    Role update(Role role);

    void delete(Long roleId);

    Role addRole(Role role);

    boolean deleteRole(Role role);
}