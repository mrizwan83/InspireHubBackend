package com.rizzywebworks.InspireHub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties properties;
    public String issue(long userId, String email, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("e", email)
                .withClaim("r", roles)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
    // This creates a JWT Token with fields for email, userId, and roles, need to change the secret and can
    // potentially change hashing algorithm and secret key later
}

/**
 * Component responsible for issuing (creating) a JWT (JSON Web Token) for a user based on their
 * userId, email, and granted authorities (roles). This class is intended to be used in the authentication
 * process to generate a token that can be used for secure communication between the client and server.
 *
 * Components:
 * - JwtProperties: A configuration class containing properties for JWT, such as the secret key.
 * - Duration and Instant: Java 8 time classes used to handle token expiration duration.
 * - ChronoUnit: Enum representing units of time used in conjunction with Duration.
 *
 * Constructor:
 * - JwtIssuer(JwtProperties properties): Constructor with a required JwtProperties dependency
 *   injected. JwtProperties contains configuration details for JWT, including the secret key.
 *
 * Method:
 * - issue(long userId, String email, List<String> roles): Issues a JWT token with the given userId,
 *   email, and roles. The token includes claims for subject (userId), expiration time, email, and roles.
 *   The token is signed using the HMAC256 algorithm and the secret key specified in JwtProperties.
 *
 * Note: The token expiration is set to 1 day from the current time. The secret key used for signing
 *       the token is obtained from JwtProperties, providing flexibility to change it later.
 *
 * Important: The secret key and hashing algorithm used for signing the token should be kept secure.
 *             Ensure that the expiration time is appropriate for your application's security requirements.
 *
 * Usage: Inject this component where needed to issue JWT tokens for user authentication.
 */
