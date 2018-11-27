package com.pony.business.services;

import java.util.List;
import java.util.Optional;

import com.pony.entities.models.Role;

public interface RoleService {

    List<Role> findAll();

    Optional<Role> findById(Long roleId);

    Role findByName(String roleName);
  
    Role insert(Role role);

    Role update(Role role);

    void delete(Long roleId);

    Role addRole(Role role);

    boolean deleteRole(Role role);

    void insetDefaultRoles();
}