package com.rizzywebworks.InspireHub.controller;

import com.rizzywebworks.InspireHub.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {
    @GetMapping("/")
    public String greeting() {
        return "Hello, World!";
    }


    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
        return "If you see this you are logged in as user " + principal.getEmail()
                + " User ID:" + principal.getUserId();
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal UserPrincipal principal) {
        return "If you see this, then you are an ADMIN, User ID: " + principal.getUserId();
    }
}

/**
 * Controller class for handling basic greeting and secured endpoints.
 * Utilizes Spring Security's @AuthenticationPrincipal to access authenticated user details.
 *
 * Annotations:
 * - @RestController: Indicates that this class contains RESTful endpoints.
 * - @RequiredArgsConstructor: Lombok annotation for generating a constructor with required dependencies.
 *
 * Endpoints:
 * - @GetMapping("/"): Returns a simple greeting message.
 * - @GetMapping("/secured"): Returns a message for authenticated users, displaying user details.
 * - @GetMapping("/admin"): Returns a message for authenticated users with ADMIN role, displaying user details.
 *
 * Usage: Register this class as a component in the Spring application context to handle web requests.
 */


