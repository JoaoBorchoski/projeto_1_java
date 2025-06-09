package com.example.projeto1.infrastructure.security;

import com.example.projeto1.domain.User;
import com.example.projeto1.domain.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro de autenticação JWT que intercepta requisições protegidas 
 * e valida o token JWT do header Authorization.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Obter o header Authorization da requisição
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Sem token JWT no header -> segue adiante sem autenticar
            filterChain.doFilter(request, response);
            return;
        }

        // Extrair token após "Bearer "
        String token = authHeader.substring(7);
        // Validar token e obter login do usuário
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username == null) {
            // Token inválido ou expirado -> não autentica (continua cadeia de filtros)
            filterChain.doFilter(request, response);
            return;
        }

        // Carregar usuário do banco para garantir que existe e obter detalhes (opcional)
        User user;
        try {
            user = userRepository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        } catch (UsernameNotFoundException ex) {
            // Usuário do token não existe mais no sistema
            filterChain.doFilter(request, response);
            return;
        }

        // Cria objeto de autenticação para o usuário
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Collections.emptyList());
        // Anexa detalhes da requisição (opcional)
        ((UsernamePasswordAuthenticationToken) auth)
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // Registra a autenticação no contexto de segurança
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Continua a execução da cadeia de filtros com o usuário autenticado
        filterChain.doFilter(request, response);
    }
}
