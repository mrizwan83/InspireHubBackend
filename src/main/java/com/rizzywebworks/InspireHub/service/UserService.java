package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String EXISTING_EMAIL = "test1@test.com";
    public Optional<UserEntity> findByEmail(String email) {
        //TODO: move to a database
        if (! EXISTING_EMAIL.equalsIgnoreCase(email)) return Optional.empty();

        var user = new UserEntity();
        user.setId(1L);
        user.setEmail(EXISTING_EMAIL);
        user.setPassword("$2a$12$k5VgWoHvR8nQizmsGw.05OqH7vwGraGXWJWX04zkRZ81J6jFIMW3G"); // test
        user.setRole("ROLE_ADMIN");
        user.setExtraInfo("OG Dizzy ADMIN");
        return Optional.of(user);
    }
}
