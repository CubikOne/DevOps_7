package com.s21.devops.sample.sessionservice.Repository;

import com.s21.devops.sample.sessionservice.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByRole(String role);
}
