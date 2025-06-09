package com.example.projeto1.api.controller;

import com.example.projeto1.api.request.LoginRequest;
import com.example.projeto1.api.response.JwtResponse;
import com.example.projeto1.api.response.JwtResponseWithRefresh;
import com.example.projeto1.application.AuthService;
import com.example.projeto1.infrastructure.security.JwtTokenUtil.JwtTokens;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest.getLogin(), loginRequest.getPassword());
            JwtResponse responseBody = new JwtResponse(token);
            return ResponseEntity.ok(responseBody);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário ou senha inválidos");
        }
    }

    @PostMapping("/loginWithRefresh")
    public ResponseEntity<?> loginWithRefresh(@RequestBody LoginRequest loginRequest) {
        try {
            JwtTokens token = authService.loginWithRefresh(loginRequest.getLogin(), loginRequest.getPassword());
            JwtResponseWithRefresh responseBody = new JwtResponseWithRefresh(token.getAccessToken(), token.getRefreshToken());
            return ResponseEntity.ok(responseBody);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário ou senha inválidos");
        }
    }
}
