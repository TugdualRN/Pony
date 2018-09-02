package com.pony.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pony.models.Role;
import com.pony.models.User;
import com.pony.repositories.RoleRepository;
import com.pony.repositories.UserRepository;
import com.pony.services.UserService;
import com.pony.utils.RegisterResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private static Logger _logger = LoggerFactory.getLogger(UserService.class);

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
    public void delete(User user) {
        _userRepository.delete(user);
    }

    @Override
    public synchronized RegisterResult createUser(User user, String password) {

        user.setNormalizedUserName(user.getUserName().toUpperCase());
        user.setNormalizedMail(user.getMail().toUpperCase());

        RegisterResult registerResult = new RegisterResult();
        if (!this.exists(user, registerResult)) {
            String hashedPassword = _passwordEncoder.encode(password);
            user.setPasswordHash(hashedPassword);

            // Add USER role as the default role
            Role role = _roleRepository.findByName("USER");
            user.getRoles().add(role);

            User savedUser = _userRepository.save(user);
            if (savedUser != null) {
    
                registerResult.setUser(savedUser); 

                _logger.info("Created User " + user.getUserName() + " with Mail " + user.getMail());

                return registerResult;
            }

            _logger.info("Failed to created User " + user.getUserName() + " with Mail " + user.getMail());
        } else {
            _logger.info("User " + user.getNormalizedUserName() + " with Mail " + user.getNormalizedMail() + "was already present in Database");
        }

        return registerResult;
    }

    /**
     * Verify the given User doesn"t already exists
     */
    private boolean exists(User user, RegisterResult registerResult) {

        boolean exists = false;

        String normalizedUserName = user.getNormalizedUserName();
        String normalizedMail = user.getNormalizedMail();

        if (_userRepository.findByNormalizedUserName(normalizedUserName) != null) {
            registerResult.setIsUserNameAlreadyTaken(true);
            exists = true;
        }

        if (_userRepository.findByNormalizedMail(normalizedMail) != null) {
            registerResult.setIsMailAlreadyTaken(true);
            exists = true;
        }

        return exists;
    }

    public boolean hasRole(User user, Role role) {
        for (Role userRole : user.getRoles()) {
            if (userRole.getName().equals(role.getName())) {
                return true;
            }
        }

        return false;
    }

    public User addRoleToUser(User user, Role role) {
        if (!hasRole(user, role)) {
            user.getRoles().add(role);

            return _userRepository.save(user);
        }

        return null;
    }

    public User removeRoleToUser(User user, Role role) {
        if (hasRole(user, role)) {
            user.getRoles().removeIf(x -> x.getId() == role.getId());

            return _userRepository.save(user);
        }

        return null;
    }
}