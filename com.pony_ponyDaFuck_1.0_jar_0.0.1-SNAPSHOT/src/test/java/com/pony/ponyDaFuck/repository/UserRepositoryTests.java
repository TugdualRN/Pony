package com.pony.ponyDaFuck.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pony.models.User;
import com.pony.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTests {

	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testFindByUserName(){
		
		String userName = "kevin";
		User user = userRepository.findByUserName(userName);
		assertEquals(userName, user.getUserName());
	}
	@Test
	public void testFindByNormalizedUserName(){
		
		String userName = "KEVIN";
		User user = userRepository.findByNormalizedUserName(userName);
		assertEquals(userName, user.getNormalizedUserName());
	}
	@Test
	public void testFindByMail(){
		
		String userMail = "guingrich.kevin@gmail.com";
		User user = userRepository.findByMail(userMail);
		assertEquals(userMail, user.getMail());
	}
	@Test
	public void testFindByNormalizedMail(){
		
		String userMail = "GUINGRICH.KEVIN@GMAIL.COM";
		User user = userRepository.findByNormalizedMail(userMail);
		assertEquals(userMail, user.getNormalizedMail());
	}
}
