package com.s21.devops.sample.sessionservice.Service;

import com.s21.devops.sample.sessionservice.Model.CustomUserDetails;
import com.s21.devops.sample.sessionservice.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserServiceImplementation userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        User user = userService.findByUid(UUID.fromString(username));
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }
}
