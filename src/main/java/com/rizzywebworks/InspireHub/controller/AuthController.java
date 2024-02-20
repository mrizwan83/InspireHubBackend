package com.rizzywebworks.InspireHub.controller;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.model.*;
import com.rizzywebworks.InspireHub.service.AuthService;
import com.rizzywebworks.InspireHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated LoginRequest request) {
        try {
            LoginResponse loginResponse = authService.attemptLogin(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(Map.of("accessToken", loginResponse.getAccessToken()));
        } catch (AuthenticationFailedException e) {
            // Handle invalid email/password combination
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated RegisterRequest request) {
        try {
            // Register the user
            UserRecord registeredUser = userService.registerUser(request);

            // Attempt login to generate access token
            LoginResponse loginResponse = authService.attemptLogin(request.getEmail(), request.getPassword());

            // Combine user record and access token in the response body
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("user", registeredUser);
            responseBody.put("accessToken", loginResponse.getAccessToken());

            // Return the response with OK status
            return ResponseEntity.ok().body(responseBody);
        } catch (AuthenticationFailedException e) {
            // Handle errors from the service layer (e.g., email already exists, invalid password)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred during registration: " + e.getMessage()));
        }
    }

}

