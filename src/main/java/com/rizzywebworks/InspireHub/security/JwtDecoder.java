package com.rizzywebworks.InspireHub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties properties;
    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                .build()
                .verify(token);
    }
}

/**
 * Component responsible for decoding (verifying) a JWT (JSON Web Token) to retrieve its claims and
 * ensure its integrity. This class is intended to be used in the authentication process to validate
 * tokens received from clients and extract information about the authenticated user.
 *
 * Components:
 * - JwtProperties: A configuration class containing properties for JWT, such as the secret key.
 *
 * Constructor:
 * - JwtDecoder(JwtProperties properties): Constructor with a required JwtProperties dependency
 *   injected. JwtProperties contains configuration details for JWT, including the secret key.
 *
 * Method:
 * - decode(String token): Decodes (verifies) the provided JWT token using the HMAC256 algorithm
 *   and the secret key specified in JwtProperties. It returns a DecodedJWT object containing the
 *   token claims if the verification is successful.
 *
 * Note: The JwtDecoder assumes that the secret key used for signing the token matches the secret key
 *       specified in JwtProperties. Proper handling of exceptions and potential token validation issues
 *       should be implemented based on your application's requirements.
 *
 * Important: The secret key used for decoding should be kept secure. Ensure proper error handling
 *             for potential token verification failures.
 *
 * Usage: Inject this component where needed to decode and verify JWT tokens during authentication.
 */

