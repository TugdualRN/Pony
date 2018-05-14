/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services.impl;

import com.pony.exceptions.NoSuchEntityException;
import com.pony.exceptions.UniqueEntityViolationException;
import com.pony.models.Users;
import com.pony.repositories.UsersRepository;
import com.pony.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gotug
 */
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UsersRepository usersRepository) {
	this.passwordEncoder = passwordEncoder;
	this.usersRepository = usersRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Users> findAll() {
	return usersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Users findById(Long userId) throws NoSuchEntityException {

	Users user = usersRepository.findOne(userId);

	if (user != null) {
	    return user;
	} else {
	    throw new NoSuchEntityException(userId, Users.class);
	}
    }

    @Override
    public Users insert(Users user) throws UniqueEntityViolationException {

	Users userByLogin = usersRepository.findByLogin(user.getLogin());
	if (userByLogin != null) {
	    throw new UniqueEntityViolationException("login", user.getLogin(), Users.class);
	}

	user.setPassword(passwordEncoder.encode(user.getPassword()));

	return usersRepository.save(user);
    }

    @Override
    public Users update(Long userId, Users user) throws UniqueEntityViolationException, NoSuchEntityException {

	Users userById = usersRepository.findOne(userId);

	if (userById == null) {
	    throw new NoSuchEntityException(userId, Users.class);
	}

	Users userByLogin = usersRepository.findByLogin(user.getLogin());
	if (userByLogin != null && !userId.equals(userByLogin.getId())) {
	    throw new UniqueEntityViolationException("login", user.getLogin(), Users.class);
	}

	// We don't allow to modify the user's login
	
	String password = user.getPassword();
	if (password != null && !"".equals(password)) { // If the password wasn't changed; password will be null
	    userById.setPassword(passwordEncoder.encode(user.getPassword()));
	}
	userById.setLastname(user.getLastname());
	userById.setFirstname(user.getFirstname());
	userById.setPhone(user.getPhone());
	userById.setMail(user.getMail());
	userById.setRoles(user.getRoles());

	return usersRepository.save(userById);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
	usersRepository.delete(userId);
    }
}
