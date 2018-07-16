package com.pony.services.impl;

import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pony.enumerations.TokenType;
import com.pony.models.Token;
import com.pony.services.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	@Override
	public Token generateToken(TokenType tokenType) {
		return new Token(tokenType, UUID.randomUUID(), LocalDateTime.now());
	}
}