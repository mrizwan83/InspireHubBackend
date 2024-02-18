package com.rizzywebworks.InspireHub.model;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    public UserEntity toUserEntity(RegisterRequest request){

        // convert registration request to userEntity
        return UserEntity.builder()
                .email(request.getEmail())

                // hash password
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role("ROLE_USER")
                .build();
    }

    public UserRecord toUserRecord(UserEntity user) {
        return new UserRecord(user.getId(), user.getEmail(), user.getUsername());
    }
}
