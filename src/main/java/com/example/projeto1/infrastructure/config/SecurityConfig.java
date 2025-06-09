package com.example.projeto1.infrastructure.config;

import com.example.projeto1.infrastructure.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    /**
     * Define o PasswordEncoder a ser usado na aplicação (BCrypt).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa BCrypt com strength padrão (10)
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura a cadeia de filtros de segurança HTTP, desabilitando sessões e 
     * definindo a autorização via JWT.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, 
                                                   JwtAuthenticationFilter jwtFilter) throws Exception {
        http.csrf(csrf -> csrf.disable());  // Desabilita CSRF para APIs stateless
        http.sessionManagement(sm -> 
            sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Sem sessões (stateless)
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "loginWithRefresh").permitAll()   // /login não exige autenticação
            .anyRequest().authenticated()            // outras requisições exigem token válido
        );
        // Adiciona o filtro JWT antes do filtro padrão de autenticação do Spring Security
        http.addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
