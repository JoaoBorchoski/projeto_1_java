package com.example.projeto1.api.request;

/**
 * DTO para representar os dados da requisição de login.
 */
public class LoginRequest {
    private String login;
    private String password;
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public void setLogin(String login) { this.login = login; }
    public void setPassword(String password) { this.password = password; }
}
