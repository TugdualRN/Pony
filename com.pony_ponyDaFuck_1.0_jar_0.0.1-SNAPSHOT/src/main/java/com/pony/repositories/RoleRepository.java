package com.pony.repositories;

import com.pony.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
     
    public Role findByName(String roleName);
}