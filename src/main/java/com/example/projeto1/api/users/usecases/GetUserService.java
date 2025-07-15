package com.example.projeto1.api.users.usecases;

import org.springframework.stereotype.Service;

import com.example.projeto1.api.exceptions.ResourceNotFoundException;
import com.example.projeto1.api.users.dto.GetUserRequest;
import com.example.projeto1.api.users.dto.UserResponse;
import com.example.projeto1.api.users.entity.User;
import com.example.projeto1.api.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserService {
    private final UserRepository userRepository;

    public UserResponse execute(GetUserRequest req) {
        User user = userRepository.findById(req.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + req.getId()));

        UserResponse resp = new UserResponse();
        resp.setId(user.getId());
        resp.setName(user.getName());
        resp.setLogin(user.getLogin());
        resp.setAdmin(user.isAdmin());
        resp.setSuperUser(user.isSuperUser());
        resp.setDisabled(user.isDisabled());
        resp.setCreatedAt(user.getCreatedAt());
        resp.setUpdatedAt(user.getUpdatedAt());

        return resp;
    }
}
