package com.example.projeto1.api.exceptions; // Sugestão de pacote, ajuste se necessário

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private long timestamp;
    private String message;
    private String description;
}