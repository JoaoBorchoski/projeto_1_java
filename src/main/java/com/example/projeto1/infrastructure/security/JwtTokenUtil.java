package com.example.projeto1.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.secretRefresh}")
    private String jwtSecretRefresh;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${jwt.jwtRefreshExpirationMs}")
    private long jwtRefreshExpirationMs;

    private Algorithm algorithm;
    private Algorithm refreshAlgorithm;
    private JWTVerifier jwtVerifier;

    @jakarta.annotation.PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(jwtSecret);
        this.refreshAlgorithm = Algorithm.HMAC256(jwtSecretRefresh);
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    public String generateToken(String login) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        String token = JWT.create()
                .withSubject(login)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
        return token;
    }

    public JwtTokens generateTokens(String login) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        Date refreshExpiryDate = new Date(now.getTime() + jwtRefreshExpirationMs);

        String accessToken = JWT.create()
                .withSubject(login)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(login)
                .withIssuedAt(now)
                .withExpiresAt(refreshExpiryDate)
                .sign(refreshAlgorithm);

        return new JwtTokens(accessToken, refreshToken);
    }

    public String getUsernameFromToken(String token) {
        try {
            return jwtVerifier.verify(token).getSubject();
        } catch (Exception e) {
            return null; 
        }
    }

    public static class JwtTokens {
        private String accessToken;
        private String refreshToken;

        public JwtTokens(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
