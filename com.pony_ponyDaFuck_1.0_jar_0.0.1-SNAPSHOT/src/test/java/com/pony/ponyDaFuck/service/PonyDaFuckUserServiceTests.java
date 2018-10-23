package com.pony.ponyDaFuck.service;
import static org.junit.Assert.assertEquals;

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

import com.pony.models.Role;
import com.pony.models.User;
import com.pony.services.RoleService;
import com.pony.services.UserService;
import com.pony.utils.RegisterResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PonyDaFuckUserServiceTests {

	@Autowired
	private UserService testedUserService;
	
	@Autowired
	private RoleService roleService;
	
	@Test
	public void testFindById(){
		User user = testedUserService.findById(1L);
		String expected = "guingrich.kevin@gmail.com";
		assertEquals(expected, user.getMail());
	}
	@Test 
	public void testFindAll(){
		List<User> usersList = testedUserService.findAll();
		int all = 1;
		assertEquals(all, usersList.size());
	}
	@Test
	public void testFindByUserName(){
		User user = testedUserService.findByUserName("kevin");
		String expected = "guingrich.kevin@gmail.com";
		assertEquals(expected, user.getMail());
	}
	@Test
	public void testFindByNormalizedUserName(){
		User user = testedUserService.findByNormalizedUserName("KEVIN");
		String expected = "guingrich.kevin@gmail.com";
		assertEquals(expected, user.getMail());
	}
	@Test
	public void testFindByMail(){
		User user = testedUserService.findByMail("guingrich.kevin@gmail.com");
		String expected = "guingrich.kevin@gmail.com";
		assertEquals(expected, user.getMail());
	}
	@Test
	public void testFindByNormalizedMail(){
		User user = testedUserService.findByNormalizedMail("GUINGRICH.KEVIN@GMAIL.COM");
		String expected = "guingrich.kevin@gmail.com";
		assertEquals(expected, user.getMail());
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testUpdate(){
		
		User user = testedUserService.findById(1L);
		String name = "name updated";
		user.setFirstName(name);
		user = testedUserService.update(user);
		assertEquals(name, user.getFirstName());
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testDelete(){
		
		User userToDelete = testedUserService.findById(1L);
		testedUserService.delete(userToDelete);
		User user = testedUserService.findById(1L);
		assertEquals(null, user);
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testCreateUser(){
		
		User user = new User();
		user.setMail("test@create.user");
		user.setUserName("toto");
		String password = "azerty1234";
		RegisterResult registerResult = testedUserService.createUser(user, password);
		assertEquals(true, registerResult.isValid());
	}
	@Test
	public void testHasRole(){
		
		User user = testedUserService.findById(1L);
		Role role = roleService.findById(4L);
		
		assertEquals(true, testedUserService.hasRole(user, role));
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testAddRoleToUser(){
		
		User user = testedUserService.findById(1L);
		Role role = roleService.findById(5L);
		
		assertEquals(user, testedUserService.addRoleToUser(user, role));
	}
	@Test
	@Transactional
    @Rollback(true)
	public void testRemoveRoleToUser(){
		
		User user = testedUserService.findById(1L);
		Role role = roleService.findById(4L);
		
		assertEquals(user, testedUserService.removeRoleToUser(user, role));
	}
}
