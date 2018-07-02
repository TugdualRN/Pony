<<<<<<< Updated upstream
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import com.pony.models.Role;
import com.pony.models.User;
import com.pony.repositories.RoleRepository;
import com.pony.repositories.UserRepository;
import com.pony.services.UserService;


/**
 *
 * @author Gotug
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger _logger = Logger.getLogger(UserService.class);

    private final BCryptPasswordEncoder _passwordEncoder;
    private final UserRepository _userRepository;
    private final RoleRepository _roleRepository;
    

    @Autowired
    public UserServiceImpl(
        BCryptPasswordEncoder passwordEncoder, 
        UserRepository userRepository, 
        RoleRepository roleRepository
    ) {
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
    public User findById(Long userId) {

        return _userRepository.findOne(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByMail(String mail) {
        
        return _userRepository.findByMail(mail);
    }

    @Override
    public User update(User user) {
        return _userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        _userRepository.delete(userId);
    }

    public synchronized User createUser(User user, String password) {

        user.setNormalizedUserName(user.getUserName().toUpperCase());
        user.setNormalizedMail(user.getMail().toUpperCase());

        if (!this.exists(user)) {
            String hashedPassword = _passwordEncoder.encode(password);
            user.setPasswordHash(hashedPassword);

            Role baseRole = _roleRepository.findByName("USER");
        
            List<Role> roles = new ArrayList<Role>();
            roles.add(baseRole);
    
            user.setRoles(roles);

            User savedUser = _userRepository.save(user);
            if (savedUser != null) {
                _logger.info("Created User " + user.getUserName() + " with Mail " + user.getMail());

                return savedUser;
            }

            _logger.info("Failed to created User " + user.getUserName() + " with Mail " + user.getMail());
        } else {
            _logger.info("User " + user.getNormalizedUserName() + " with Mail " + user.getNormalizedMail() + "was already present in Database");
        }

        return null;
    }

    /**
     * Verify the given User doesn"t already exists
     */
    private boolean exists(User user) {

        String normalizedUserName = user.getNormalizedUserName();
        String normalizedMail = user.getNormalizedMail();

        if (_userRepository.findByNormalizedUserName(normalizedUserName) != null) {
            return true;
        }

        if (_userRepository.findByNormalizedMail(normalizedMail) != null) {
            return true;
        }

        return false;
    }
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import com.pony.enumerations.TokenType;
import com.pony.models.Role;
import com.pony.models.Token;
import com.pony.models.User;
import com.pony.repositories.RoleRepository;
import com.pony.repositories.UserRepository;
import com.pony.services.UserService;


/**
 *
 * @author Gotug
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger _logger = Logger.getLogger(UserService.class);

    private final BCryptPasswordEncoder _passwordEncoder;
    private final UserRepository _userRepository;
    private final RoleRepository _roleRepository;
    

    @Autowired
    public UserServiceImpl(
        BCryptPasswordEncoder passwordEncoder, 
        UserRepository userRepository, 
        RoleRepository roleRepository
    ) {
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
    public User findById(Long userId) {

        return _userRepository.findOne(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByMail(String mail) {
        
        return _userRepository.findByMail(mail);
    }

    @Override
    public User update(User user) {
        return _userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        _userRepository.delete(userId);
    }

    public synchronized User createUser(User user, String password) {

        user.setNormalizedUserName(user.getUserName().toUpperCase());
        user.setNormalizedMail(user.getMail().toUpperCase());

        if (!this.exists(user)) {
            String hashedPassword = _passwordEncoder.encode(password);
            user.setPasswordHash(hashedPassword);

            Role baseRole = _roleRepository.findByName("USER");
        
            List<Role> roles = new ArrayList<Role>();
            roles.add(baseRole);
            user.setRoles(roles);

            List<Token> tokens = new ArrayList<Token>();
            tokens.add(new Token(TokenType.ACTIVATE_ACCOUNT, UUID.randomUUID(), LocalDate.now()));

            User savedUser = _userRepository.save(user);
            if (savedUser != null) {
                _logger.info("Created User " + user.getUserName() + " with Mail " + user.getMail());

                return savedUser;
            }

            _logger.info("Failed to created User " + user.getUserName() + " with Mail " + user.getMail());
        } else {
            _logger.info("User " + user.getNormalizedUserName() + " with Mail " + user.getNormalizedMail() + "was already present in Database");
        }

        return null;
    }

    /**
     * Verify the given User doesn"t already exists
     */
    private boolean exists(User user) {

        String normalizedUserName = user.getNormalizedUserName();
        String normalizedMail = user.getNormalizedMail();

        if (_userRepository.findByNormalizedUserName(normalizedUserName) != null) {
            return true;
        }

        if (_userRepository.findByNormalizedMail(normalizedMail) != null) {
            return true;
        }

        return false;
    }
>>>>>>> Stashed changes
}