package com.s21.devops.sample.sessionservice.Service;

import com.s21.devops.sample.sessionservice.Exception.RoleNotFoundException;
import com.s21.devops.sample.sessionservice.Exception.UserAlreadyExistsException;
import com.s21.devops.sample.sessionservice.Model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    public UUID createUser(final String username, final String password)
            throws RoleNotFoundException, UserAlreadyExistsException;
    public User findByLogin(final String username)
            throws UsernameNotFoundException;
    public User findByLoginAndPassword(final String login, final String password)
            throws RoleNotFoundException;
    public User findByUid(UUID uuid);
}
