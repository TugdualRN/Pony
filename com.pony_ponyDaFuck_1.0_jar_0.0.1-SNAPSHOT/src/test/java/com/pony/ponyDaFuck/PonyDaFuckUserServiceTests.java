package com.pony.ponyDaFuck;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.pony.models.User;
import com.pony.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PonyDaFuckUserServiceTests {

	@Autowired
	private UserService testedUserService;
	
	@Test
	public void testFindById(){
			User user = testedUserService.findById(23L);
			String expected = "guingrich.kevin@gmail.com";
			assertEquals(expected, user.getMail());
	}
}
