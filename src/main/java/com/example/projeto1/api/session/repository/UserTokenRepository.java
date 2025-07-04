package com.example.projeto1.api.session.repository;

import com.example.projeto1.api.session.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {
    Optional<UserToken> findByRefreshToken(String refreshToken);
}
