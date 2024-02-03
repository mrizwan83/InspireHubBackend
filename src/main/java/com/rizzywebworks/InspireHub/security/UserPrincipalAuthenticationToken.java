package com.rizzywebworks.InspireHub.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;
    public UserPrincipalAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return principal;
    }
}

/**
 * Represents an authentication token used in Spring Security to carry information
 * about the authenticated user principal. This class extends AbstractAuthenticationToken
 * and is designed to hold the UserPrincipal object.
 *
 * The authentication token is created when a user is successfully authenticated, and it
 * contains the user's principal along with the granted authorities (roles).
 *
 * Fields:
 * - principal: The UserPrincipal representing the authenticated user.
 *
 * Constructor:
 * - UserPrincipalAuthenticationToken(UserPrincipal principal): Constructor to create
 *   the authentication token with the provided UserPrincipal. The granted authorities
 *   are obtained from the UserPrincipal, and the token is set as authenticated.
 *
 * Methods:
 * - getCredentials(): Override method to get the credentials (not used in this example).
 * - getPrincipal(): Override method to get the authenticated user's principal.
 *
 * Note: The getCredentials() method returns null, assuming credentials retrieval is not
 *       implemented in this example and is handled elsewhere in the authentication process.
 *
 * Important: This class is set as authenticated in the constructor, assuming that the
 *             authentication is successful upon token creation. If your application
 *             requires additional checks, consider implementing those in a custom way.
 */

