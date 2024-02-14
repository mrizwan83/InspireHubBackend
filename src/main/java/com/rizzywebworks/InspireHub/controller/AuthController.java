package com.rizzywebworks.InspireHub.controller;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.model.AuthenticationFailedException;
import com.rizzywebworks.InspireHub.model.LoginRequest;
import com.rizzywebworks.InspireHub.model.LoginResponse;
import com.rizzywebworks.InspireHub.model.RegisterRequest;
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

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;
//    @PostMapping("/login")
//    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
//       return authService.attemptLogin(request.getEmail(), request.getPassword());
//    }
    // This is for testing to make sure the JWT is being build correctly with the correct
    // fields to identify the user

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated LoginRequest request) {
        try {
            LoginResponse loginResponse = authService.attemptLogin(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(Map.of("accessToken", loginResponse.getAccessToken()));
        } catch (AuthenticationFailedException e) {
            // Handle invalid email/password combination
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email/password combination"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated RegisterRequest request) {
        UserEntity registeredUser = userService.registerUser(request);
        // Create a ResponseEntity with OK status and the registered user as body
//        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

        try {
            LoginResponse loginResponse = authService.attemptLogin(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(Map.of("accessToken", loginResponse.getAccessToken()));
        } catch (AuthenticationFailedException e) {
            // Handle invalid email/password combination
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email/password combination"));
        }
    }
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
