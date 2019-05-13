package com.pony.spring.security;

import com.pony.entities.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pony.entities.models.User;
import com.pony.data.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository _userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this._userRepository = userRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        System.out.println("\n USER TRIED TO AUTHENTICATE HIMLSELF WITH EMAIL " + login + "\n");

        String normalizedMail = login.toUpperCase();
        User user = _userRepository.findByNormalizedMail(normalizedMail);
        if (user == null) {
            String print = "Couldn't find the logged user based on the given identitifer {0}" + login;
            System.out.println(print);

            throw new UsernameNotFoundException("User not found.");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPasswordHash(), grantedAuthorities);

//
//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//
//        System.out.println("\n USER TRIED TO AUTHENTICATE HIMLSELF WITH EMAIL " + login + "\n");
//
//        String normalizedMail = login.toUpperCase();
//
//        User user = _userRepository.findByNormalizedMail(normalizedMail);
//
//        if (user == null) {
//            String print = String.format("Couldn't find the logged user based on the given identitifer {0}", login);
//            System.out.println(print);
//
//            throw new UsernameNotFoundException("User not found.");
//        }
//
//        return new ConnectedUserDetails(user);
//    }
}
}