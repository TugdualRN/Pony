package com.pony.services.impl;

import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pony.enumerations.TokenType;
import com.pony.models.Token;
import com.pony.models.User;
import com.pony.services.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Override
	public Token generateToken(TokenType tokenType, User user) {
		
		Token token = new Token(tokenType, UUID.randomUUID(), LocalDateTime.now());
		token.setUser(user);

		return token;
	}
}