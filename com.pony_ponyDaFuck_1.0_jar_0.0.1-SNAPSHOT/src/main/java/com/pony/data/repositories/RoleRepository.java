package com.pony.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.pony.entities.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

    public Optional<Role> findById(Long id);

    public Role findByName(String roleName);
}