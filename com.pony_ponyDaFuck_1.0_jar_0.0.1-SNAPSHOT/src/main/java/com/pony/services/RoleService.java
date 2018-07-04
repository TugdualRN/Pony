package com.pony.services;

import java.util.List;

import com.pony.models.Role;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long roleId);

    Role insert(Role role);

    Role update(Long roleId, Role role);

    void delete(Long roleId);
}