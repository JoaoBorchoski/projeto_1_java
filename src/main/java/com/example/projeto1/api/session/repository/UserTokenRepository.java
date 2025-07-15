package com.example.projeto1.api.session.repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projeto1.api.session.entity.UserToken;
import com.example.projeto1.api.users.entity.User;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {
    Optional<UserToken> findByRefreshToken(String refreshToken);

    Optional<UserToken> findByUserAndExpiresDateAfter(User user, Instant now);
}
