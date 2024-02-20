package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.model.*;
import com.rizzywebworks.InspireHub.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public UserRecord registerUser(RegisterRequest request) {
        try {
            // Check if email already exists
            if (findByEmail(request.getEmail()).isPresent()) {
                throw new AuthenticationFailedException("Email already exists.");
            }

            // Perform password validations here if needed
            if (!isValidPassword(request.getPassword())) {
                throw new AuthenticationFailedException("Invalid password, password length must be at least 8 characters.");
            }

            // Check if username is unique
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new AuthenticationFailedException("Username already exists.");
            }

            // Convert RegisterRequest to UserEntity
            UserEntity userEntity = userMapper.toUserEntity(request);

            // Save the user to the database
            UserEntity savedUser = userRepository.save(userEntity);

            // Convert saved UserEntity to UserRecord and return
            return userMapper.toUserRecord(savedUser);
        } catch (AuthenticationFailedException e) {
            // Rethrow the exception to be caught and handled in the controller
            throw e;
        } catch (Exception e) {
            // Log the error or handle it accordingly
            throw new AuthenticationFailedException("User registration failed: " + e.getMessage());
        }
    }


    // Add any additional methods for password validation if needed
    private boolean isValidPassword(String password) {
        // Implement your password validation logic here
        // For example, you can check the length, character requirements, etc.
        return password.length() >= 8; // Example: Password should be at least 8 characters long
    }

    @Override
    public List<UserRecord> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userMapper::toUserRecord)
                .collect(Collectors.toList());
    }

    @Override
    public UserRecord getUserById(Long id) {
        Optional<UserEntity> userOptional =  userRepository.findById(id);

        if (userOptional.isPresent()){
            UserEntity userEntity = userOptional.get();
            return userMapper.toUserRecord(userEntity);
        }else {
            throw new AuthenticationFailedException("User not found with ID: " + id);
        }
    }


}
