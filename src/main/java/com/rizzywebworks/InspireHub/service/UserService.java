package com.rizzywebworks.InspireHub.service;

import com.rizzywebworks.InspireHub.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String EXISTING_EMAIL = "test1@test.com";

    private static final String ANOTHER_EMAIL = "user1@test.com";

    public Optional<UserEntity> findByEmail(String email) {
        //TODO: move to a database
        if (EXISTING_EMAIL.equalsIgnoreCase(email)) {
            var user = new UserEntity();
            user.setId(1L);
            user.setEmail(EXISTING_EMAIL);
            user.setPassword("$2a$12$k5VgWoHvR8nQizmsGw.05OqH7vwGraGXWJWX04zkRZ81J6jFIMW3G"); // test
            user.setRole("ROLE_ADMIN");
            user.setExtraInfo("OG Dizzy ADMIN");
            return Optional.of(user);
        } else if (ANOTHER_EMAIL.equalsIgnoreCase(email)) {
            var user = new UserEntity();
            user.setId(99L);
            user.setEmail(ANOTHER_EMAIL);
            user.setPassword("$2a$12$k5VgWoHvR8nQizmsGw.05OqH7vwGraGXWJWX04zkRZ81J6jFIMW3G"); // test
            user.setRole("ROLE_USER");
            user.setExtraInfo("a regular user");
            return Optional.of(user);
        }
        return Optional.empty();
    }
}

/**
 * Service class for managing user-related operations.
 *
 * Annotations:
 * - @Service: Indicates that this class is a service component in the Spring application context.
 *
 * Methods:
 * - findByEmail(String email): Retrieves a user entity by email for testing purposes.
 *   TODO: Move to a database in a production environment.
 *
 * Note: The findByEmail method returns a pre-defined user entity based on the provided email for testing.
 *       In a real-world scenario, this logic would be replaced with database queries.
 *
 * Usage: Register this class as a component in the Spring application context to provide user-related services.
 */
