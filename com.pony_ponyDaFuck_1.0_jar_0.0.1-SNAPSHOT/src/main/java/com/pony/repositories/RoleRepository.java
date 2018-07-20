package com.pony.repositories;

import com.pony.models.Role;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
    public Role findByName(String roleName);
}