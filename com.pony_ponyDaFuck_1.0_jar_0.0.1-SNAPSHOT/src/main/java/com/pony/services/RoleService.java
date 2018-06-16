/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services;

import java.util.List;

import com.pony.models.Role;

/**
 *
 * @author Gotug
 */
public interface RoleService {

    List<Role> findAll();

    Role findById(Long roleId);

    Role insert(Role role);

    Role update(Long roleId, Role role);

    void delete(Long roleId);
}