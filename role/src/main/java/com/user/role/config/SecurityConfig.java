package com.user.role.config;

import com.user.role.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthentication authentication;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/auth").permitAll()
                        .requestMatchers("/users/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated()
                ).addFilterBefore(authentication, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
