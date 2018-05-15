/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.security;

import com.pony.models.Roles;
import com.pony.models.Users;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Gotug
 */
public class ConnectedUserDetails implements UserDetails {

     private final String login;
     private final String password;
     private final String lastname;
     private final String firstname;
     private final String roleName;

     public ConnectedUserDetails(Users user) {
          this.login = user.getLogin();
          this.password = user.getPassword();
          this.lastname = user.getLastname();
          this.firstname = user.getFirstname();

          Roles userRole = user.getRoles();
          roleName = userRole != null ? userRole.getName() : null;
     }

     public String getLogin() {
          return login;
     }

     public String getLastname() {
          return lastname;
     }

     public String getFirstname() {
          return firstname;
     }

     public String getRoleName() {
          return roleName;
     }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

	final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

	// TODO
	/*for (final Privilege privilege : user.getPrivileges()) {
	    authorities.add(new SimpleGrantedAuthority(privilege.getName()));
	}*/
	grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER")); // To garantee the authentification

	return grantedAuthorities;
    }

     @Override
     public String getPassword() {
          return password;
     }

     @Override
     public String getUsername() {
          return login;
     }

     @Override
     public boolean isAccountNonExpired() {
          return true;
     }

     @Override
     public boolean isAccountNonLocked() {
          return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     public boolean isEnabled() {
          return true;
     }

}
