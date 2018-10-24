package com.pony.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pony.enumerations.TokenType;
import com.pony.models.Role;
import com.pony.models.Token;
import com.pony.models.User;
import com.pony.repositories.UserRepository;
import com.pony.services.RoleService;
import com.pony.services.TokenService;
import com.pony.services.UserService;
import com.pony.utils.Mailer;
import com.pony.utils.RegisterResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private static Logger _logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository _userRepository;

    private final TokenService _tokenService;
    private final RoleService _roleService;

    private final BCryptPasswordEncoder _passwordEncoder;
    private final Mailer _mailer;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository, 
        TokenService tokenService,
        RoleService roleService,
        BCryptPasswordEncoder passwordEncoder,
        Mailer mailer
    ) {
        _userRepository = userRepository;
        _tokenService = tokenService;
        _roleService = roleService;
        _passwordEncoder = passwordEncoder;
        _mailer = mailer;
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
            Role role = _roleService.findByName("USER");
            user.getRoles().add(role);

            User savedUser = _userRepository.save(user);
            if (savedUser != null) {
    
                registerResult.setUser(savedUser); 

                _logger.info("Created User " + user.getUserName() + " with Mail " + user.getMail());

                return registerResult;
            }

            _logger.info("Failed to created User " + user.getUserName() + " with Mail " + user.getMail());
        } 
        else {
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
            
            _logger.info(
                "Removed role " 
                + role.getName() 
                + " to user " 
                + user.getMail());

            return _userRepository.save(user);
        }

        _logger.error(
            "Tried to add role " 
            + role.getName() 
            + " from user " 
            + user.getMail() 
            + "but he already has it");

        return user;
    }

    public User removeRoleToUser(User user, Role role) {
        if (hasRole(user, role)) {
            user.getRoles().removeIf(x -> x.getId() == role.getId());
            
            _logger.info(
                "Removed role " 
                + role.getName() 
                + " to user " 
                + user.getMail());

            return _userRepository.save(user);
        }

        _logger.error(
            "Tried to remove role " 
            + role.getName() 
            + " from user " 
            + user.getMail() 
            + "but he did not have the role in the first place");

        return user;
    }

    public User updatePassword(User user, String password) {
        String hashedPassword = _passwordEncoder.encode(password);
        user.setPasswordHash(hashedPassword);

        return _userRepository.save(user);
    }

    public User generateToken(User user, TokenType tokenType) throws MailException {

        Token token = _tokenService.generateToken(tokenType, user);
        user.getTokens().add(token);

        if (tokenType == TokenType.ACTIVATE_ACCOUNT) {
            _mailer.SendRegisterMail(user, token);
        } else {
            _mailer.SendResetPassword(user, token);
        }

        return _userRepository.save(user);
    }
}