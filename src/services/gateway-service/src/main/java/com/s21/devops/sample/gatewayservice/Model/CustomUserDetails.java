package com.s21.devops.sample.gatewayservice.Model;

import com.s21.devops.sample.gatewayservice.Communication.UserUidRes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private String userUid;
    private String password;

    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetails fromUserUidResToCustomUserDetails(UserUidRes user) {
        CustomUserDetails c = new CustomUserDetails();
        c.userUid = user.getUserUid().toString();
        c.password = "";
        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
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