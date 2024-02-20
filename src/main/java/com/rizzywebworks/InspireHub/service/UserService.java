package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import com.rizzywebworks.InspireHub.model.RegisterRequest;
import com.rizzywebworks.InspireHub.model.UserRecord;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    UserRecord registerUser(RegisterRequest request);

    List<UserRecord> getAllUsers();

    UserRecord getUserById(Long id);
}
