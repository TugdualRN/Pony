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
import com.pony.utils.MailService;
import com.pony.utils.RegisterResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private final Logger _logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserRepository _userRepository;

    private final TokenService _tokenService;
    private final RoleService _roleService;
    private final MailService _mailService;

    private final BCryptPasswordEncoder _passwordEncoder;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository, 
        TokenService tokenService,
        RoleService roleService,
        MailService mailService,
        BCryptPasswordEncoder passwordEncoder) {
        _userRepository = userRepository;
        _tokenService = tokenService;
        _roleService = roleService;
        _passwordEncoder = passwordEncoder;
        _mailService = mailService;
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
    public RegisterResult createUser(User user, String password) {

        user.setNormalizedUserName(user.getUserName().toUpperCase());
        user.setNormalizedMail(user.getMail().toUpperCase());

        RegisterResult registerResult = new RegisterResult();
        if (!this.exists(user, registerResult)) {
            String hashedPassword = _passwordEncoder.encode(password);
            user.setPasswordHash(hashedPassword);

            // Add USER role as the default role
            Role role = _roleService.findByName("USER");

            // Maybe the DB was wiped and there are no roles, we create them and retry
            if (role == null) {
                _roleService.insetDefaultRoles();
                role = _roleService.findByName("USER");
            }

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

        if (user.getRoles().stream().anyMatch(x -> x.getName().equals(role.getName()))) {
            return true;
        }

        return false;
    }

    public User addRoleToUser(User user, Role role) {
        if (!hasRole(user, role)) {
            user.getRoles().add(role);
            User savedUser = _userRepository.save(user);
            
            _logger.info("Added role {} to user {}", role.getName(), savedUser.getMail()); 

            return savedUser;
        }

        _logger.error("Tried to add role {} to user {} but he already has it", role.getName(), user.getMail()); 

        return user;
    }

    public User removeRoleToUser(User user, Role role) {
        if (hasRole(user, role)) {
            user.getRoles().removeIf(x -> x.getId() == role.getId());
            User savedUser = _userRepository.save(user);
            
            _logger.info("Removed role {} to user {}", role.getName(), savedUser.getMail()); 

            return savedUser;
        }

        _logger.error("Tried to remove role {} from user {} but he did not have the role in the first place", role.getName(), user.getMail()); 

        return user;
    }

    public User updatePassword(User user, String password) {
        String hashedPassword = _passwordEncoder.encode(password);
        user.setPasswordHash(hashedPassword);
        User savedUser = _userRepository.save(user);

        _logger.info("Updated password for user {}", user.getMail());

        return savedUser;
    }

    /**
     * Create and link the given token to the user and send a confirmation mail
     */
    public User generateToken(User user, TokenType tokenType) throws MailException {

        Token token = _tokenService.generateToken(tokenType, user);
        user.getTokens().add(token);
        User savedUser = _userRepository.save(user);

        _logger.info("Created token {} for user {}", token.getType(), user.getMail());

        if (tokenType == TokenType.ACTIVATE_ACCOUNT) {
            _mailService.SendRegisterMail(user, token);
        } else {
            _mailService.SendResetPassword(user, token);
        }

        return savedUser;
    }
}