package com.rizzywebworks.InspireHub.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .email(jwt.getClaim("e").asString())
                .authorities(extractAuthoritiesFromClaim(jwt))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("r");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}

/**
 * Component responsible for converting a decoded JWT (JSON Web Token) into a UserPrincipal object,
 * which represents the authenticated user's details. This class is intended to be used as a part of
 * the authentication process in a Spring Security context.
 *
 * Components:
 * - DecodedJWT: Represents a decoded JWT, providing access to its claims and information.
 * - SimpleGrantedAuthority: A Spring Security class representing a granted authority (role).
 *
 * Methods:
 * - convert(DecodedJWT jwt): Converts a decoded JWT into a UserPrincipal object. The UserPrincipal
 *   is built using the userId, email, and authorities extracted from the JWT claims.
 * - extractAuthoritiesFromClaim(DecodedJWT jwt): Extracts authorities (roles) from the "r" claim
 *   in the decoded JWT. Returns a list of SimpleGrantedAuthority objects.
 *
 * Note: The JWT claims are assumed to contain the following information:
 *   - "sub" (subject): Represents the userId.
 *   - "e" (email): Represents the user's email.
 *   - "r" (roles): Represents the user's granted authorities (roles).
 *
 * Important: This converter assumes that the JWT contains the necessary claims and is used in a
 *             context where the JWT is already verified and decoded. Proper validation and error
 *             handling should be implemented based on your application's requirements.
 *
 * Usage: Inject this component where needed to convert decoded JWTs into UserPrincipal objects.
 */

