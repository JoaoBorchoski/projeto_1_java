package com.example.projeto1.api.users.usecases;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projeto1.api.users.dto.PatchUserRequest;
import com.example.projeto1.api.users.dto.UserResponse;
import com.example.projeto1.api.users.entity.User;
import com.example.projeto1.api.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatchUserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse execute(UUID id, PatchUserRequest req) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

    userRepository.findByLogin(req.getLogin())
        .ifPresent(u -> {
          if (!u.getId().equals(id)) {
            throw new IllegalArgumentException("Login já cadastrado");
          }
        });

    if (req.getName() != null) {
      user.setName(req.getName());
    }
    if (req.getLogin() != null) {
      user.setLogin(req.getLogin());
    }
    if (req.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(req.getPassword()));
    }
    if (req.getAdmin() != null) {
      user.setAdmin(req.getAdmin());
    }

    User updatedUser = userRepository.save(user);

    UserResponse resp = new UserResponse();
    resp.setId(updatedUser.getId());
    resp.setName(updatedUser.getName());
    resp.setLogin(updatedUser.getLogin());
    resp.setAdmin(updatedUser.isAdmin());
    resp.setSuperUser(updatedUser.isSuperUser());
    resp.setDisabled(updatedUser.isDisabled());
    resp.setCreatedAt(updatedUser.getCreatedAt());
    resp.setUpdatedAt(updatedUser.getUpdatedAt());

    return resp;
  }

}
