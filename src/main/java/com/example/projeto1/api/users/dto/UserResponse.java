package com.example.projeto1.api.users.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String login;
    private boolean isAdmin;
    private boolean isSuperUser;
    private boolean isDisabled;
    private Instant createdAt;
    private Instant updatedAt;
}
