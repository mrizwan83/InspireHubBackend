package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.UserEntity;
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
