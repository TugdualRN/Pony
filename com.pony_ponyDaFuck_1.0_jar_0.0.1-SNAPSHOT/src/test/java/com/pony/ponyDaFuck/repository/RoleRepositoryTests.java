package com.pony.ponyDaFuck.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pony.models.Role;
import com.pony.data.repositories.RoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoleRepositoryTests {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Test
	public void testFindByName(){
		
		String name = "ADMIN";
		Role role = roleRepository.findByName(name);
		assertEquals(name, role.getName());
	}
}
