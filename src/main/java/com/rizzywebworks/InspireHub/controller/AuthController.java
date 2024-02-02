package com.rizzywebworks.InspireHub.controller;

import com.rizzywebworks.InspireHub.model.LoginRequest;
import com.rizzywebworks.InspireHub.model.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return LoginResponse.builder()
                .accessToken("blah blah blah")
                .build();
    }
}
