/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services;

import java.util.List;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.models.User;

/**
 *
 * @author Gotug
 */
public interface UserService {

    List<User> findAll();

    User findById(Long userId) throws NoSuchEntityException;

    User findByMail(String mail);

    User insert(User user) throws UniqueEntityViolationException;

    User update(Long userId, User user) throws UniqueEntityViolationException, NoSuchEntityException;

    void delete(Long userId);
}