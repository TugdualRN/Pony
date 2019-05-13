package com.pony.business.services;

import com.pony.entities.models.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long roleId);

    Role findByName(String roleName);
  
    Role insert(Role role);

    Role update(Role role);

    void delete(Long roleId);

    Role addRole(Role role);

    boolean deleteRole(Role role);

    void insetDefaultRoles();
}