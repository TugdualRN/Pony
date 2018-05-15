/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.models.Users;
import java.util.List;

/**
 *
 * @author Gotug
 */

public interface UserService {

     List<Users> findAll();

     Users findById(Long userId) throws NoSuchEntityException;

     Users insert(Users user) throws UniqueEntityViolationException;

     Users update(Long userId, Users user) throws UniqueEntityViolationException, NoSuchEntityException;

     void delete(Long userId);
}
