package com.example.projeto1.api.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto1.api.users.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);
}
