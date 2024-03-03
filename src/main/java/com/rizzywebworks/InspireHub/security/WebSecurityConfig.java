package com.rizzywebworks.InspireHub.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailService customUserDetailService;

    private final UnauthorizedHandler unauthorizedHandler;

    private final PasswordEncoder passwordEncoder;
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler);

                http.securityMatcher("/**")
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/apod/fetch").permitAll()
                        .requestMatchers("/api/v1/quotes/fetch").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        return http.build();


    }



    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }

    private PasswordEncoder passwordEncoder() {

        return passwordEncoder;
    }
}

/**
 * Configuration class defining the security setup for the web application using Spring Security.
 * This class configures security rules, authentication mechanisms, and enables JWT-based
 * authentication for securing endpoints.
 *
 * Components:
 * - JwtAuthenticationFilter: Custom filter for JWT-based authentication.
 * - CustomUserDetailService: Custom implementation of UserDetailsService for loading user details.
 *
 * Configuration annotations:
 * - @Configuration: Indicates that this class is a configuration class for the Spring application context.
 * - @EnableWebSecurity: Enables Spring Security features for web applications.
 *
 * Constructor:
 * - WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
 *                    CustomUserDetailService customUserDetailService):
 *   Constructor with the required dependencies injected.
 *
 * Beans:
 * - applicationSecurity(HttpSecurity http): Configures the security settings for the application.
 *   It adds the JwtAuthenticationFilter before the UsernamePasswordAuthenticationFilter to process JWTs.
 *   Disables CORS, CSRF, and session creation to enforce stateless authentication. Configures URL
 *   authorization, allowing unauthenticated access to "/", "/auth/login", and requiring authentication
 *   for any other request.
 *
 * - passwordEncoder(): Bean for BCryptPasswordEncoder used for password hashing.
 *
 * - authenticationManager(HttpSecurity http): Bean for configuring the AuthenticationManager,
 *   specifying the userDetailsService and passwordEncoder for authentication.
 *
 * Note: This configuration assumes that the "/" and "/auth/login" endpoints are public, while any other
 *       endpoint requires authentication. Adjust the URL patterns based on your application's requirements.
 *
 * Usage: Register this configuration class in the Spring application context to apply the defined security settings.
 */


