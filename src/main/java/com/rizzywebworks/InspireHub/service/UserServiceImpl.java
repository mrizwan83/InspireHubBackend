package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.model.AuthenticationFailedException;
import com.rizzywebworks.InspireHub.model.RegisterRequest;
import com.rizzywebworks.InspireHub.model.User;
import com.rizzywebworks.InspireHub.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public UserEntity registerUser(RegisterRequest request) {
        // Check if email already exists
        if (findByEmail(request.getEmail()).isPresent()) {
            throw new AuthenticationFailedException("Email already exists");
        }

        // Perform password validations here if needed
        if (!isValidPassword(request.getPassword())) {
            throw new AuthenticationFailedException("Invalid password");
        }

        // Check if username is unique
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthenticationFailedException("Username already exists");
        }
        // Create a new UserEntity
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                // You may need to hash the password here
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        // Save the user to the database
        return userRepository.save(user);
    }

    // Add any additional methods for password validation if needed
    private boolean isValidPassword(String password) {
        // Implement your password validation logic here
        // For example, you can check the length, character requirements, etc.
        return password.length() >= 8; // Example: Password should be at least 8 characters long
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntity -> new User(
                        userEntity.getId(),
                        userEntity.getEmail(),
                        userEntity.getPassword(),
                        userEntity.getUsername(),
                        userEntity.getRole(),
                        userEntity.getExtraInfo()
                )).toList();
    }

}
