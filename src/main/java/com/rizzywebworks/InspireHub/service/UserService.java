package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.model.RegisterRequest;
import com.rizzywebworks.InspireHub.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    UserEntity registerUser(RegisterRequest request);

    List<User> getAllUsers();
}
