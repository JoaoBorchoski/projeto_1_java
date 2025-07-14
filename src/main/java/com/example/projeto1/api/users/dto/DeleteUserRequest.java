package com.example.projeto1.api.users.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteUserRequest {
  @NotBlank
  private UUID id;
}
