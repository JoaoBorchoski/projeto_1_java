package com.example.projeto1.api.users.usecases.create;

import com.example.projeto1.api.users.dto.CreateUserRequest;
import com.example.projeto1.api.users.dto.UserResponse;
import com.example.projeto1.api.users.entity.User;
import com.example.projeto1.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
// import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse execute(CreateUserRequest req) {
        // 1) checar se já existe
        userRepository.findByLogin(req.getLogin())
            .ifPresent(u -> { throw new IllegalArgumentException("Email já cadastrado"); });

        // 2) montar entidade
        User user = new User();
        // user.setId(UUID.randomUUID());
        user.setName(req.getName());
        user.setLogin(req.getLogin());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        // flags default já vêm false

        // 3) salvar
        User saved = userRepository.save(user);

        // 4) mapear para response
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
