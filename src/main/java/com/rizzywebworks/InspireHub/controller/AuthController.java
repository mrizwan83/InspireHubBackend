package com.rizzywebworks.InspireHub.controller;

import com.rizzywebworks.InspireHub.model.LoginRequest;
import com.rizzywebworks.InspireHub.model.LoginResponse;
import com.rizzywebworks.InspireHub.security.JwtIssuer;
import com.rizzywebworks.InspireHub.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtIssuer;

    private final AuthenticationManager authenticationManager;
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
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
    // This is for testing to make sure the JWT is being build correctly with the correct
    // fields to identify the user
}
/**
 * Controller class for handling authentication-related endpoints.
 *
 * Annotations:
 * - @RestController: Indicates that this class contains RESTful endpoints.
 * - @RequiredArgsConstructor: Lombok annotation for generating a constructor with required dependencies.
 *
 * Dependencies:
 * - JwtIssuer: Component for issuing JWT tokens.
 * - AuthenticationManager: Spring Security component for managing authentication.
 *
 * Endpoints:
 * - @PostMapping("/auth/login"): Authenticates the user with the provided credentials and returns a JWT token.
 *
 * Method:
 * - login(@RequestBody @Validated LoginRequest request): Authenticates the user using the AuthenticationManager,
 *   sets the authentication in the SecurityContextHolder, and issues a JWT token using JwtIssuer.
 *   Returns the generated token in the LoginResponse.
 *
 * Note: The login endpoint is for testing and ensures that the JWT is being built correctly with the correct
 *       fields to identify the user.
 *
 * Usage: Register this class as a component in the Spring application context to handle authentication requests.
 */