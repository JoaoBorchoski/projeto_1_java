package com.example.projeto1.api.users.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PatchUserRequest { 
    private String name; 

    @Email 
    private String login;

    private String password;

    private Boolean admin;
}