/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.security;

import com.pony.models.Users;
import com.pony.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gotug
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

     private final UsersRepository usersRepository;

     @Autowired
     public UserDetailsServiceImpl(UsersRepository usersRepository) {
          this.usersRepository = usersRepository;
     }

     @Override
     public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
          Users user = usersRepository.findByLogin(login);

          if (user == null) {
               System.out.println("user == null");
	    throw new UsernameNotFoundException("User not found.");
              
              // ====================== DEFAULT USER MODEL ======================
              // Uncomment for the first connect
              
//               user = new Users();
//               user.setLogin("tug");
//               user.setPassword(new BCryptPasswordEncoder().encode("123"));
//
//               System.out.println("====================== DEFAULT USER CREATED ======================");

          }

          return new ConnectedUserDetails(user);
     }
}
