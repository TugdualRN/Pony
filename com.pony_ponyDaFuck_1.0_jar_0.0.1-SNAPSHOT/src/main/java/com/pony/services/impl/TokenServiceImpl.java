package com.pony.services.impl;

import java.util.List;
import java.util.UUID;
import java.time.Duration;
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

	@Override
	public Token findToken(String tokenValue, List<Token> tokens) {
		return tokens
			.stream()
			.filter(x -> x.getValue().toString() == tokenValue)
			.findFirst()
			.get();
	}

	@Override
	public Token findToken(String tokenValue, List<Token> tokens, TokenType tokenType) {
		return tokens
		.stream()
		.filter(x -> x.getValue().toString() == tokenValue && x.getType() == tokenType)
		.findFirst()
		.get();
	}

	@Override
	public boolean isValidToken(Token token) {

		if (token == null)
			return false;

		return Duration.between(token.getCreationdate(), LocalDateTime.now()).toHours() < 48;
	}
}