package com.example.projeto1.api.users.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projeto1.api.exceptions.BadRequestException;
import com.example.projeto1.api.users.dto.CreateUserRequest;
import com.example.projeto1.api.users.dto.UserResponse;
import com.example.projeto1.api.users.entity.User;
import com.example.projeto1.api.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;
// import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse execute(CreateUserRequest req) {
        userRepository.findByLogin(req.getLogin())
                .ifPresent(u -> {
                    throw new BadRequestException("Email já cadastrado");
                });

        User user = new User();
        user.setName(req.getName());
        user.setLogin(req.getLogin());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        User saved = userRepository.save(user);

        UserResponse resp = new UserResponse();
        resp.setId(saved.getId());
        resp.setName(saved.getName());
        resp.setLogin(saved.getLogin());
        resp.setAdmin(saved.isAdmin());
        resp.setSuperUser(saved.isSuperUser());
        resp.setDisabled(saved.isDisabled());
        resp.setCreatedAt(saved.getCreatedAt());
        resp.setUpdatedAt(saved.getUpdatedAt());
        return resp;
    }
}
