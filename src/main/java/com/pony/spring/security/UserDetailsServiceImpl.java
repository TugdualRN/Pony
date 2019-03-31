package com.pony.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pony.entities.models.User;
import com.pony.data.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository _userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this._userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        System.out.println("\n USER TRIED TO AUTHENTICATE HIMLSELF WITH EMAIL " + login + "\n");

        String normalizedMail = login.toUpperCase();

        User user = _userRepository.findByNormalizedMail(normalizedMail);

        if (user == null) {
            String print = String.format("Couldn't find the logged user based on the given identitifer {0}", login);
            System.out.println(print);

            throw new UsernameNotFoundException("User not found.");
        }

        return new ConnectedUserDetails(user);
    }
}