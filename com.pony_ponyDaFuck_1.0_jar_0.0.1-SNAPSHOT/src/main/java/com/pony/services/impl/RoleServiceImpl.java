/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services.impl;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.models.Roles;
import com.pony.repositories.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pony.services.RoleService;

/**
 *
 * @author Gotug
 */
@Service
public class RoleServiceImpl implements RoleService {

     private final RoleRepository roleRepository;

     @Autowired
     public RoleServiceImpl(RoleRepository roleRepository) {
          this.roleRepository = roleRepository;
     }

     @Override
     @Transactional(readOnly = true)
     public List<Roles> findAll() {
          return roleRepository.findAll();
     }

     @Override
     @Transactional(readOnly = true)
     public Roles findById(Long roleId) throws NoSuchEntityException {

          Roles role = roleRepository.findOne(roleId);

          if (role != null) {
               return role;
          } else {
               throw new NoSuchEntityException(roleId, Roles.class);
          }
     }

     @Override
     public Roles insert(Roles role) throws UniqueEntityViolationException {
          Roles roleByName = roleRepository.findByName(role.getName());

          if (roleByName != null) {
               throw new UniqueEntityViolationException("name", role.getName(), Roles.class);
          }

          return roleRepository.save(role);
     }

     @Override
     public Roles update(Long roleId, Roles role) throws UniqueEntityViolationException, NoSuchEntityException {

          Roles roleById = roleRepository.findOne(roleId);

          if (roleById == null) {
               throw new NoSuchEntityException(roleId, Roles.class);
          }

          Roles roleByName = roleRepository.findByName(role.getName());
          if (roleByName != null && !roleId.equals(roleByName.getId())) {
               throw new UniqueEntityViolationException("name", role.getName(), Roles.class);
          }

          roleById.setName(role.getName());

          return roleRepository.save(roleById);
     }

     @Override
     @Transactional
     public void delete(Long roleId) {
          roleRepository.delete(roleId);
     }
}
