/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.repositories;

import com.pony.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Gotug
 */
public interface UserRepository extends JpaRepository<User, Long> {
     
    public User findByUserName(String userName);

    public User findByMail(String mail);

    public User findByNormalizedMail(String normalizedMail);
}