package com.example.projeto1.api.users.usecases.create;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.projeto1.api.users.dto.CreateUserRequest;
import com.example.projeto1.api.users.dto.UserResponse;

import jakarta.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class CreateUserController {

    private final CreateUserService createUserService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest req) {
        UserResponse user = createUserService.execute(req);
        // retorna 201 e Location: /users/{id}
        return ResponseEntity
                .created(URI.create("/users/" + user.getId()))
                .body(user);
    }
}
