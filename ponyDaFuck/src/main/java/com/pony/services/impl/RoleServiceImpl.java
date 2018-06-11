/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services.impl;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.models.Role;
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

    private final RoleRepository _roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this._roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return _roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long roleId) throws NoSuchEntityException {

        Role role = _roleRepository.findOne(roleId);

        if (role != null) {
            return role;
        } else {
            throw new NoSuchEntityException(roleId, Role.class);
        }
    }

    @Override
    public Role insert(Role role) throws UniqueEntityViolationException {
        Role roleByName = _roleRepository.findByName(role.getName());

        if (roleByName != null) {
            throw new UniqueEntityViolationException("name", role.getName(), Role.class);
        }

        return _roleRepository.save(role);
    }

    @Override
    public Role update(Long roleId, Role role) throws UniqueEntityViolationException, NoSuchEntityException {

        Role roleById = _roleRepository.findOne(roleId);

        if (roleById == null) {
            throw new NoSuchEntityException(roleId, Role.class);
        }

        Role roleByName = _roleRepository.findByName(role.getName());
        if (roleByName != null && !roleId.equals(roleByName.getId())) {
            throw new UniqueEntityViolationException("name", role.getName(), Role.class);
        }

        roleById.setName(role.getName());

        return _roleRepository.save(roleById);
    }

    @Override
    @Transactional
    public void delete(Long roleId) {
    _roleRepository.delete(roleId);
    }
}