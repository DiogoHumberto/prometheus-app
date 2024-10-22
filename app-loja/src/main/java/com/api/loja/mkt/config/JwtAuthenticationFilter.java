package com.api.loja.mkt.config;

import com.api.loja.mkt.model.UserModel;
import com.api.loja.mkt.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String HEADER_PREFIX = "Bearer ";

    private AuthService authService;

    public JwtAuthenticationFilter(AuthService authService) {
        super();
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);

        if (StringUtils.hasText(token) && this.authService.isTokenValid(token)) {
            autenticarClient(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarClient(String token) {

        UserModel usuario = this.authService.getUsuarioAutenticado(token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                AuthorityUtils.NO_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    private String recuperarToken(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);// Authorization
        if (!StringUtils.hasText(token) || !token.startsWith(HEADER_PREFIX)) {
            return null;
        }
        return token.substring(7, token.length());
    }
}
