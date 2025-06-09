package com.example.projeto1.api.response;

/**
 * DTO para representar a resposta de sucesso do login (token JWT retornado).
 */
public class JwtResponse {
    private String token;
    public JwtResponse(String token) { this.token = token; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}

