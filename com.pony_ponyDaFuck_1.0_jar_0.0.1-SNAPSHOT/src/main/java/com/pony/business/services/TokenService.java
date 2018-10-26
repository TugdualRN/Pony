package com.pony.business.services;

import com.pony.entities.models.Token;
import com.pony.entities.models.User;

import java.util.List;

import com.pony.enumerations.TokenType;

public interface TokenService {

    Token generateToken(TokenType tokenType, User user);

    Token findToken(String tokenValue, List<Token> tokens);

    Token findToken(String tokenValue, List<Token> tokens, TokenType tokenType);

    void consumeToken(Token token);

    boolean isValidToken(Token token);
}