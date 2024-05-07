package com.s21.devops.sample.reportservice.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class ServiceDetails implements UserDetails {

    private String userUid;
    private String password;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static ServiceDetails fromUserEntityToCustomServiceDetails(UUID serviceUid) {
        ServiceDetails c = new ServiceDetails();
        c.userUid = serviceUid.toString();
        c.password = "";
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_SERVICE"));
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userUid;
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