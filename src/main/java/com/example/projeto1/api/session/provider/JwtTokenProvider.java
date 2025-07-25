package com.example.projeto1.api.session.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException; 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException; 
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException; 
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException; 
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function; 

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private long accessTokenExpirationMs;

    @Value("${jwt.jwtRefreshExpirationMs}")
    private long refreshTokenExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String userLogin) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(accessTokenExpirationMs);

        return Jwts.builder()
                .setSubject(userLogin)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Duration getRefreshValidity() {
        return Duration.ofMillis(refreshTokenExpirationMs);
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new SignatureException("Assinatura JWT inválida.", ex);
        } catch (MalformedJwtException ex) {
            throw new MalformedJwtException("Token JWT malformado.", ex);
        } catch (ExpiredJwtException ex) {
            throw new ExpiredJwtException(null, null, "Token JWT expirado.", ex);
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("Token JWT não suportado.", ex);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("String de claims JWT vazia.", ex);
        }
    }

    public String extractUserIdentifier(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userIdentifier = extractUserIdentifier(token);
        try {
            validateToken(token); 
        } catch (Exception e) {
            return false;
        }
        return (userIdentifier.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}