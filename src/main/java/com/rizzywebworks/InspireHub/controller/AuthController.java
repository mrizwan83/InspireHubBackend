package com.rizzywebworks.InspireHub.controller;

import com.rizzywebworks.InspireHub.model.LoginRequest;
import com.rizzywebworks.InspireHub.model.LoginResponse;
import com.rizzywebworks.InspireHub.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
       return authService.attemptLogin(request.getEmail(), request.getPassword());
    }
    // This is for testing to make sure the JWT is being build correctly with the correct
    // fields to identify the user
}

/*
 Controller class for handling authentication-related endpoints.
 Annotations:
 - @RestController: Indicates that this class contains REST endpoints.
 - @RequiredArgsConstructor: Lombok annotation for generating a constructor with required dependencies.
 Dependencies:
 - AuthService: Service for handling authentication logic.
 Endpoints:
 - @PostMapping("/auth/login"): Initiates the login process by forwarding the request to the
   AuthService to attempt user authentication. Returns a LoginResponse containing a JWT token.
 Note: The login endpoint is designed to test the correct construction of the JWT with the
       necessary fields to identify the user.
 Usage: Register this class as a component in the Spring application context to handle authentication requests.
*/
