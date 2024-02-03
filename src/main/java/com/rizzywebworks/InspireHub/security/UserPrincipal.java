package com.rizzywebworks.InspireHub.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Builder
public class UserPrincipal implements UserDetails {

    private final Long userId;

    private final String email;

    private final Collection<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

/**
 * Represents a user principal in the Spring Security framework.
 * This class implements the UserDetails interface, providing essential information
 * about the user required for authentication and authorization.
 *
 * The class is annotated with Lombok's @Getter and @Builder annotations to
 * automatically generate getters for fields and a builder pattern for instantiation.
 *
 * Fields:
 * - userId: Unique identifier for the user.
 * - email: User's email address.
 * - authorities: Collection of authorities (roles) granted to the user.
 *
 * Methods:
 * - getAuthorities(): Override method to get the authorities (roles) granted to the user.
 * - getPassword(): Override method to get the user's password (not used in this example).
 * - getUsername(): Override method to get the user's username (in this case, the email address).
 * - isAccountNonExpired(): Override method to check if the user's account is non-expired.
 * - isAccountNonLocked(): Override method to check if the user's account is non-locked.
 * - isCredentialsNonExpired(): Override method to check if the user's credentials are non-expired.
 * - isEnabled(): Override method to check if the user's account is enabled.
 *
 * Note: The getPassword() method returns null, assuming password retrieval is not implemented
 *       in this example and is handled elsewhere in the authentication process.
 */

