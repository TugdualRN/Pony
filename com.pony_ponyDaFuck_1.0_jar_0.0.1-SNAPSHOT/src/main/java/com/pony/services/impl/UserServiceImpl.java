package com.pony.services.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import com.pony.models.Role;
import com.pony.models.Token;
import com.pony.models.User;
import com.pony.repositories.RoleRepository;
import com.pony.repositories.UserRepository;
import com.pony.services.UserService;
import com.pony.enumerations.TokenType;

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
    public User findByUserName(String userName) {

        return _userRepository.findByUserName(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByNormalizedUserName(String normalizedUserName) {

        return _userRepository.findByNormalizedUserName(normalizedUserName);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByMail(String mail) {
        
        return _userRepository.findByMail(mail);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByNormalizedMail(String normalizedMail) {
        
        return _userRepository.findByNormalizedMail(normalizedMail);
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

    @Override
    public synchronized User createUser(User user, String password) {

        user.setNormalizedUserName(user.getUserName().toUpperCase());
        user.setNormalizedMail(user.getMail().toUpperCase());

        if (!this.exists(user)) {
            String hashedPassword = _passwordEncoder.encode(password);
            user.setPasswordHash(hashedPassword);

            // Add USER role as the default role
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

    @Override
    public Token generateToken(TokenType type, User user) {

        Token token = new Token(type, UUID.randomUUID(), LocalDateTime.now());
        user.getTokens().add(token);

        _userRepository.save(user);

        return token;
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
}