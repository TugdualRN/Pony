package com.pony.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pony.entities.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    public Role findByName(String roleName);
}