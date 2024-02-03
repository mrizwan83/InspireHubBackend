package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.model.LoginResponse;
import com.rizzywebworks.InspireHub.security.JwtIssuer;
import com.rizzywebworks.InspireHub.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final JwtIssuer jwtIssuer;

    private final AuthenticationManager authenticationManager;
    public LoginResponse attemptLogin(String email, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}

/**
 * Service class for handling authentication-related operations.
 *
 * Annotations:
 * - @Service: Indicates that this class is a service component in the Spring application context.
 * - @RequiredArgsConstructor: Lombok annotation for generating a constructor with required dependencies.
 *
 * Dependencies:
 * - JwtIssuer: Component for issuing JWT tokens.
 * - AuthenticationManager: Spring Security component for managing authentication.
 *
 * Methods:
 * - attemptLogin(String email, String password): Attempts user authentication using the provided email and password.
 *   If successful, sets the authentication in the SecurityContextHolder and issues a JWT token.
 *
 * Usage: Register this class as a component in the Spring application context to provide authentication services.
 */
