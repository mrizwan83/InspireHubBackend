package com.rizzywebworks.InspireHub.controller;


import com.rizzywebworks.InspireHub.model.AuthenticationFailedException;
import com.rizzywebworks.InspireHub.model.UserRecord;
import com.rizzywebworks.InspireHub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public List<UserRecord> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        try {
            UserRecord user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (AuthenticationFailedException e) {
            // Log the error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred while processing your request"));
        }
    }


}
