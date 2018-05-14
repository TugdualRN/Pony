/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.models.Roles;
import java.util.List;

/**
 *
 * @author Gotug
 */
public interface RoleService {

     List<Roles> findAll();

     Roles findById(Long roleId) throws NoSuchEntityException;

     Roles insert(Roles role) throws UniqueEntityViolationException;

     Roles update(Long roleId, Roles role) throws UniqueEntityViolationException, NoSuchEntityException;

     void delete(Long roleId);
}
