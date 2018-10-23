package com.pony.security;

import com.pony.models.Role;
import com.pony.models.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This Object is Returned By UserDetailsServiceImpl to Spring Security
 * Our User Object interact with Spring Security by the UserDetails Interface Implementation
 */
public class ConnectedUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    public ConnectedUserDetails(User user) {
        this.user = user;
    }

    /**
     * This Method is Called by Spring Security to have information about user rights (Controller Access)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // We translate every role the user have into a SimpleGrantedAuthority (for Spring Security)
        for (Role role : this.user.getRoles())
        {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

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