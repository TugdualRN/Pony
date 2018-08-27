package com.pony.ponyDaFuck.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pony.enumerations.TokenType;
import com.pony.models.User;
import com.pony.services.TokenService;
import com.pony.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PonyDaFuckTokenServiceTests {
	
	@Autowired
	TokenService testedTokenService;
	@Autowired
	private UserService userService;
	
	@Test
	public void testGenerateToken(){
		
		Long userId = 1L;
		User user = userService.findById(userId);
		testedTokenService.generateToken(TokenType.ACTIVATE_ACCOUNT, user);
	}
}
