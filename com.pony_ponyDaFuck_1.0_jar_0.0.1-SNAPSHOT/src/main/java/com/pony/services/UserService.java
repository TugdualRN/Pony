/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services;

import java.util.List;

import com.pony.enumerations.TokenType;
import com.pony.models.Token;
import com.pony.models.User;

/**
 *
 * @author Gotug
 */
public interface UserService {

    List<User> findAll();

    User findById(Long userId);

    User findByMail(String mail);

    User update(User user);

    void delete(Long userId);

    User createUser(User user, String password);

    Token generateToken(TokenType token, User user);
}