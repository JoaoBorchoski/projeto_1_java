package com.example.projeto1.infrastructure.repository;

import com.example.projeto1.domain.User;
import com.example.projeto1.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementação do UserRepository do domínio, usando o JpaUserRepository para acesso ao banco.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return jpaUserRepository.findByLogin(login);
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }
}
