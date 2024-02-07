package com.rizzywebworks.InspireHub.security;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email " + username));

        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .password(user.getPassword())
                .build();
    }
}

// this is what Spring uses to load user specific data into the framework

/**
 * Custom implementation of the UserDetailsService interface used by Spring Security.
 * This class is responsible for loading user-specific details during authentication.
 *
 * Components:
 * - UserService: Service for managing user-related operations.
 *
 * Implements:
 * - UserDetailsService: Spring Security interface for loading user details.
 *
 * Constructor:
 * - CustomUserDetailService(UserService userService): Constructor with the required UserService dependency injected.
 *
 * Method:
 * - loadUserByUsername(String username): Overrides the UserDetailsService method to load user details by email.
 *   It retrieves the user from the UserService, constructs a UserPrincipal with user information, and returns it.
 *
 * Note: This service is used by Spring Security to retrieve user details during authentication.
 *
 * Usage: Register this service in the Spring application context for Spring Security to utilize during authentication.
 */

