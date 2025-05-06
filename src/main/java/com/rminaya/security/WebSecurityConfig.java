package com.rminaya.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(req -> {
                    req.pathMatchers("/login").permitAll();
                    req.pathMatchers(HttpMethod.GET, USER_OR_ADMIN_RESOURCES).hasAnyAuthority(ADMIN_ROLE, USER_ROLE);
                    req.pathMatchers(HttpMethod.POST, ADMIN_RESOURCES).hasAuthority(ADMIN_ROLE);
                    req.pathMatchers(HttpMethod.PUT, ADMIN_RESOURCES).hasAuthority(ADMIN_ROLE);
                    req.pathMatchers(HttpMethod.DELETE, ADMIN_RESOURCES).hasAuthority(ADMIN_ROLE);
                    req.anyExchange().authenticated();
                })
                .build();
    }

    private static final String[] USER_OR_ADMIN_RESOURCES = {"/v1/cursos/**", "/v1/estudiantes/**", "/v2/cursos/**", "/v2/estudiantes/**"};
    private static final String[] ADMIN_RESOURCES = {"/v1/cursos/**", "/v1/estudiantes/**", "/v1/matriculas/**", "/v2/cursos/**", "/v2/estudiantes/**", "/v2/matriculas/**"};

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
}
