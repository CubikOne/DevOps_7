package com.s21.devops.sample.sessionservice.Repository;

import com.s21.devops.sample.sessionservice.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserUid(UUID userUid);
    Optional<User> findByName(String name);
}
