package com.example.projeto1.application;

import com.example.projeto1.domain.User;
import com.example.projeto1.domain.UserRepository;
import com.example.projeto1.infrastructure.security.JwtTokenUtil;
import com.example.projeto1.infrastructure.security.JwtTokenUtil.JwtTokens;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder, 
                       JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public String login(String login, String rawPassword) throws BadCredentialsException {
        System.err.println("Buscando usuário com login: " + login);
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        String hashed = user.getPassword();
        if (!passwordEncoder.matches(rawPassword, hashed)) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = jwtTokenUtil.generateToken(user.getLogin());
        return token;
    }

    public JwtTokens loginWithRefresh(String login, String rawPassword) throws BadCredentialsException {
        System.err.println("Buscando usuário com login: " + login);
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BadCredentialsException("Credenciais inválidas"));

        String hashed = user.getPassword();
        if (!passwordEncoder.matches(rawPassword, hashed)) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        JwtTokens token = jwtTokenUtil.generateTokens(user.getLogin());
        return token;
    }
}
