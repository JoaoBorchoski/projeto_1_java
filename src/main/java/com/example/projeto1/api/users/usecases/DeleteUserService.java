package com.example.projeto1.api.users.usecases;

import org.springframework.stereotype.Service;

import com.example.projeto1.api.exceptions.ResourceNotFoundException;
import com.example.projeto1.api.users.dto.DeleteUserRequest;
import com.example.projeto1.api.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteUserService {
    private final UserRepository userRepository;

    public void execute(DeleteUserRequest req) {
        userRepository.findById(req.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + req.getId()));

                //ResourceNotFoundException colocar

        userRepository.deleteById(req.getId());
    }
}
