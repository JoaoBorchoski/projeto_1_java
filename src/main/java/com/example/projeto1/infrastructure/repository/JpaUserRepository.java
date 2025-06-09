package com.example.projeto1.infrastructure.repository;

import com.example.projeto1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório JPA gerado pelo Spring Data para a entidade User.
 */
@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByLogin(String login);
}
