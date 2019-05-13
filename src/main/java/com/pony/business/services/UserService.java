package com.pony.business.services;

import com.pony.business.utils.RegisterResult;
import com.pony.entities.models.Role;
import com.pony.entities.models.User;
import com.pony.enumerations.TokenType;
import org.springframework.mail.MailException;

import java.util.List;

public interface UserService {

    // <editor-fold defaultstate="collapsed" desc="CRUD">
    List<User> findAll();

    User findById(Long userId);

    User findByUserName(String userName);
    
    User findByNormalizedUserName(String normalizedUserName);

    User findByMail(String mail);

    User findByNormalizedMail(String normalizedMail);

    User update(User user);

    void delete(User user);
    // <editor fold/>

    RegisterResult createUser(User user, String password);

    boolean hasRole(User user, Role role);

    User addRoleToUser(User user, Role role);

    User removeRoleToUser(User user, Role role);

    User updatePassword(User user, String password);

    User linkTokenToUser(User user, TokenType tokenType) throws MailException;

    User activateUser(User user);
}