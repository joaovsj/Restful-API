package com.example.restful_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authRequest = request.getHeader("Authorization");

        // mesmo sem o Token ele apenas encerra a execução metodo
        if (authRequest == null || !authRequest.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        // caso haja o token ele extrai
        String token = authRequest.substring(7);  // removes "Bearer "
        String username = JwtUtil.extractUsername(token);

        // garante que o usuário não está já autenticado.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // interface do próprio java que retorna detalhes sobre os usuários(está linkada com seu UserRepository)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (JwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        }
    }
}
