package com.pony.ponyDaFuck.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pony.entities.models.Role;
import com.pony.business.services.RoleService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PonyDaFuckRoleServiceTests {

	@Autowired
	private RoleService testedRoleService;

  	@Test
	public void testFindById(){

		Long idRole = 3L;
		Role role = testedRoleService.findById(idRole);
		String expected = "ADMIN";
		assertEquals(expected, role.getName());
	}

	@Test
	public void testFindAll(){

		List<Role> roleList = testedRoleService.findAll();
		int all = 4;
		assertEquals(all, roleList.size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testInsert(){

		Role role = new Role();
		role.setName("ROLE_TEST");
		role = testedRoleService.insert(role);
		assertNotNull(role);
	}

	@Test
	@Transactional
    @Rollback(true)
	public void testUpdate(){
		Long idRole = 6L;
		Role role = testedRoleService.findById(idRole);
		String name = "ROLE_TEST";
		role.setName(name);
		role = testedRoleService.update(role);

		assertNotNull(role);
	}

	@Test
	@Transactional
    @Rollback(true)
	public void testDelete(){
		Long idRole = 6L;
		testedRoleService.delete(idRole);
		Role role = testedRoleService.findById(idRole);
		assertEquals(null, role);
	}

	@Test
	@Transactional
    @Rollback(true)
	public void testDeleteRole(){

		Long idRole = 6L;
		Role role = testedRoleService.findById(idRole);
		assertEquals(true, testedRoleService.deleteRole(role));
	}

	@Test
	@Transactional
    @Rollback(true)
	public void testAddRolee(){

		Role role = new Role();
		role.setName("ROLE_TEST");
		assertNotNull(testedRoleService.addRole(role));
	}
}