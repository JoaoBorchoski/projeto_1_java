package com.example.projeto1.api.session.usecases;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.projeto1.api.session.dto.LoginRequest;
import com.example.projeto1.api.session.dto.LoginResponse;
import com.example.projeto1.api.session.entity.UserToken;
import com.example.projeto1.api.session.provider.JwtTokenProvider;
import com.example.projeto1.api.session.repository.UserTokenRepository;
import com.example.projeto1.api.users.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserTokenRepository userTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));

        User user = (User) auth.getPrincipal();

        Optional<UserToken> existingActiveToken = userTokenRepository.findByUserAndExpiresDateAfter(user,
                Instant.now());

        if (existingActiveToken.isPresent()) {
            UserToken userToken = existingActiveToken.get();
            String accessToken = jwtTokenProvider.createAccessToken(user.getLogin());

            LoginResponse response = new LoginResponse();
            response.setAccessToken(accessToken);
            response.setRefreshToken(userToken.getRefreshToken());

            return response;
        } else {
            String accessToken = jwtTokenProvider.createAccessToken(user.getLogin());
            String refreshToken = UUID.randomUUID().toString();

            UserToken tokenEntity = new UserToken();
            tokenEntity.setUser(user);
            tokenEntity.setRefreshToken(refreshToken);
            tokenEntity.setExpiresDate(Instant.now().plus(jwtTokenProvider.getRefreshValidity()));
            userTokenRepository.save(tokenEntity);

            LoginResponse response = new LoginResponse();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);

            return response;
        }
    }
}