package com.user.role.config;

import com.user.role.token.TokenService;
import com.user.role.user.dto.UserNameRoleDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtAuthentication extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (headerIsNullOrNotStartsBearer(header)) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);

        if (!tokenService.isValid(token)) return;

        final UserNameRoleDTO user = tokenService.getUser(token);
        final UsernamePasswordAuthenticationToken authentication = buildUsernamePasswordToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken buildUsernamePasswordToken(final UserNameRoleDTO user) {
        return new UsernamePasswordAuthenticationToken(user.name(), null, Collections.singleton(new SimpleGrantedAuthority(user.role())));
    }

    private Boolean headerIsNullOrNotStartsBearer(final String header) {
        return Objects.isNull(header) || !header.startsWith("Bearer ");
    }

}
