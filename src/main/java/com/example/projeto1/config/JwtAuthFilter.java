package com.example.projeto1.config;

import com.example.projeto1.api.session.provider.JwtTokenProvider; 
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider; 
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userIdentifier;

        // 1. Verifica se o cabeçalho Authorization existe e tem o formato Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continua sem autenticação JWT
            return;
        }

        // 2. Extrai o token JWT
        jwt = authHeader.substring(7); // Remove "Bearer "

        // 3. Extrai o identificador do usuário do token usando o JwtTokenProvider
        userIdentifier = jwtTokenProvider.extractUserIdentifier(jwt); // <<-- USO AQUI

        // 4. Se o identificador for válido e não houver autenticação no contexto
        if (userIdentifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 5. Carrega os detalhes do usuário usando o UserDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userIdentifier);

            // 6. Valida o token usando o JwtTokenProvider e, se válido, define a autenticação no SecurityContext
            if (jwtTokenProvider.isTokenValid(jwt, userDetails)) { // <<-- USO AQUI
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credenciais são nulas, o token já é a credencial
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // Continua para o próximo filtro
    }
}