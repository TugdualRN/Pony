package com.pony.spring.security;

import com.pony.entities.models.Role;
import com.pony.entities.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This Object is Returned By UserDetailsServiceImpl to Spring Security
 * Our User Object interact with Spring Security by the UserDetails Interface Implementation
 */
public class ConnectedUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    ConnectedUserDetails(User user) {
        this.user = user;
    }

    /**
     * This Method is Called by Spring Security to have information about user rights (Controller Access)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            List<SimpleGrantedAuthority> authorities=new ArrayList<>();
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
//
//        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//        // We translate every role the user have into a SimpleGrantedAuthority (for Spring Security)
//        for (Role role : this.user.getRoles())
//        {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
        return grantedAuthorities;
    }

    // <editor-fold defaultstate="collapsed" desc="Getter/Setters">
    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getMail();
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
        return user.getIsActive();
    }
    // </editor-fold>
}