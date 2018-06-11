/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services.impl;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.models.Role;
import com.pony.models.User;
import com.pony.repositories.RoleRepository;
import com.pony.repositories.UserRepository;
import com.pony.services.UserService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gotug
 */
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder _passwordEncoder;
    private final UserRepository _userRepository;
    private final RoleRepository _roleRepository;
    

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this._passwordEncoder = passwordEncoder;
        this._userRepository = userRepository;
        this._roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
	    return _userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long userId) throws NoSuchEntityException {

        User user = _userRepository.findOne(userId);

        if (user != null) {
            return user;
        } else {
            throw new NoSuchEntityException(userId, User.class);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByMail(String mail)
    {
        User user = _userRepository.findByMail(mail);

        return user;
    }

    @Override
    public User insert(User user) throws UniqueEntityViolationException {

	    User userByLogin = _userRepository.findByUserName(user.getUserName());
	    if (userByLogin != null) {
	        throw new UniqueEntityViolationException("login", user.getUserName(), User.class);
        }

        String hashedPassword = _passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        String userName = user.getUserName();
        user.setNormalizedUserName(userName.toUpperCase());

        String mail = user.getMail();
        user.setNormalizedMail(mail.toUpperCase());

        Role baseRole = _roleRepository.findByName("USER");
        
        List<Role> roles = new ArrayList<Role>();
        roles.add(baseRole);

        user.setRoles(roles);

	    return _userRepository.save(user);
    }

    @Override
    public User update(Long userId, User user) throws UniqueEntityViolationException, NoSuchEntityException {

        User userById = _userRepository.findOne(userId);

        if (userById == null) {
            throw new NoSuchEntityException(userId, User.class);
        }

        User userByLogin = _userRepository.findByUserName(user.getUserName());
        if (userByLogin != null && !userId.equals(userByLogin.getId())) {
            throw new UniqueEntityViolationException("login", user.getUserName(), User.class);
        }

        // We don't allow to modify the user's login
        
        String password = user.getPasswordHash();
        if (password != null && !password.isEmpty()) { // If the password wasn't changed; password will be null
            userById.setPasswordHash(_passwordEncoder.encode(user.getPasswordHash()));
        }
        userById.setLastName(user.getLastName());
        userById.setFirstName(user.getFirstName());
        userById.setPhone(user.getPhone());
        userById.setMail(user.getMail());
        userById.setRoles(user.getRoles());

        return _userRepository.save(userById);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        _userRepository.delete(userId);
    }
}