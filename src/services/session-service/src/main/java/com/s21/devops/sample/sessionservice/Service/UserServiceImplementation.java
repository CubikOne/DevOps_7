package com.s21.devops.sample.sessionservice.Service;

import com.s21.devops.sample.sessionservice.Exception.RoleNotFoundException;
import com.s21.devops.sample.sessionservice.Exception.UserAlreadyExistsException;
import com.s21.devops.sample.sessionservice.Model.Role;
import com.s21.devops.sample.sessionservice.Model.User;
import com.s21.devops.sample.sessionservice.Repository.RoleRepository;
import com.s21.devops.sample.sessionservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UUID createUser(final String username, final String password)
            throws RoleNotFoundException, UserAlreadyExistsException {
        try {
            User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(""));
        } catch (UsernameNotFoundException ex) {
            User user = new User();
            user.setName(username);
            user.setUserUid(UUID.randomUUID());
            user.setPassword(passwordEncoder.encode(password));
            Role role = roleRepository.findByRole("ROLE_USER").orElseThrow(()
                    -> new RoleNotFoundException("Role USER was not found in the database"));
            user.setRole(role);
            userRepository.save(user);
            return user.getUserUid();
        }

        throw new UserAlreadyExistsException("User " + username + " already exists!");
    }

    @Override
    public User findByLogin(final String username)
            throws UsernameNotFoundException{
        User user = null;
        user = userRepository.findByName(username).orElseThrow(()
                -> new UsernameNotFoundException("User " + username + " was not found in the database"));
        return user;
    }

    @Override
    public User findByLoginAndPassword(final String login, final String password) {
        System.out.println("not encoded: " + password);
        System.out.println("encoded: " + passwordEncoder.encode(password));
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    public User findByUid(UUID uuid)
            throws UsernameNotFoundException {
        User user = null;
        user = userRepository.findByUserUid(uuid).orElseThrow(()
                -> new UsernameNotFoundException("User with " + uuid + " uuid was not found in the database"));
        return user;
    }

}
