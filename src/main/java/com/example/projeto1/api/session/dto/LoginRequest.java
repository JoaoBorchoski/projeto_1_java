package com.example.projeto1.api.session.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequest {
    @Email @NotBlank
    private String login;

    @NotBlank
    private String password;
}