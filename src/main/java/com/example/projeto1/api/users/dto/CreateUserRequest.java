package com.example.projeto1.api.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank
    private String name;

    @Email @NotBlank
    private String login;

    @NotBlank
    private String password;

    private boolean admin = false;
}
