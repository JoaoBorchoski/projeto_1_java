package com.example.projeto1.api.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.projeto1.api.users.dto.CreateUserRequest;
import com.example.projeto1.api.users.dto.DeleteUserRequest;
import com.example.projeto1.api.users.dto.GetUserRequest;
import com.example.projeto1.api.users.dto.PatchUserRequest;
import com.example.projeto1.api.users.dto.UserResponse;
import com.example.projeto1.api.users.usecases.CreateUserService;
import com.example.projeto1.api.users.usecases.DeleteUserService;
import com.example.projeto1.api.users.usecases.GetUserService;
import com.example.projeto1.api.users.usecases.PatchUserService;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final CreateUserService createUserService;
    private final GetUserService getUserService;
    private final PatchUserService patchUserService;
    private final DeleteUserService deleteUserService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest req) {
        UserResponse user = createUserService.execute(req);
        return ResponseEntity
                .created(URI.create("/users/" + user.getId()))
                .body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {
        GetUserRequest request = new GetUserRequest();
        request.setId(id);
        UserResponse userResponse = getUserService.execute(request);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> patch(@PathVariable UUID id, @Valid @RequestBody PatchUserRequest req) {
        UserResponse userResponse = patchUserService.execute(id, req);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setId(id);
        deleteUserService.execute(deleteUserRequest);
        return ResponseEntity.noContent().build();  
    }
    
}
