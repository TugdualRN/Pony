/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.repositories;

import com.pony.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Gotug
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
     
    public Role findByName(String roleName);
}