package com.example.projeto1.domain;

import java.util.Optional;

/**
 * Contrato de acesso a dados dos usuários.
 * (Definição de interface de repositório no domínio, implementada na camada de infraestrutura)
 */
public interface UserRepository {
    Optional<User> findByLogin(String login);
    User save(User user);
}
