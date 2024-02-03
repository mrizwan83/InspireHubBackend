package com.rizzywebworks.InspireHub.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .formLogin().disable()
                .securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();

    }
}

/**
 * Configuration class defining the security setup for the web application using Spring Security.
 * This class configures security rules, authentication mechanisms, and enables JWT-based
 * authentication for securing endpoints.
 *
 * Components:
 * - JwtAuthenticationFilter: Custom filter for JWT-based authentication.
 *
 * Configuration annotations:
 * - @Configuration: Indicates that this class is a configuration class for the Spring application context.
 * - @EnableWebSecurity: Enables Spring Security features for web applications.
 *
 * Constructor:
 * - WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter): Constructor with the required
 *   JwtAuthenticationFilter dependency injected.
 *
 * Bean:
 * - applicationSecurity(HttpSecurity http): Configures the security settings for the application.
 *   It adds the JwtAuthenticationFilter before the UsernamePasswordAuthenticationFilter to process JWTs.
 *   Disables CORS, CSRF, and session creation to enforce stateless authentication. Configures URL
 *   authorization, allowing unauthenticated access to "/", "/auth/login", and requiring authentication
 *   for any other request.
 *
 * Note: This configuration assumes that the "/" and "/auth/login" endpoints are public, while any other
 *       endpoint requires authentication. Adjust the URL patterns based on your application's requirements.
 *
 * Usage: Register this configuration class in the Spring application context to apply the defined security settings.
 */

