package com.pony.business.services;

import com.pony.entities.models.Token;
import com.pony.entities.models.User;
import com.pony.enumerations.TokenType;

import java.util.List;

public interface TokenService {

    Token generateToken(TokenType tokenType, User user);

    Token findToken(String tokenValue, List<Token> tokens);

    Token findToken(String tokenValue, List<Token> tokens, TokenType tokenType);

    void consumeToken(Token token);

    boolean isValidToken(Token token);
}