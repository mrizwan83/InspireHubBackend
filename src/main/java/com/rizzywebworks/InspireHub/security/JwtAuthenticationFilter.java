package com.rizzywebworks.InspireHub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;

    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(jwtToPrincipalConverter::convert)
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}

/**
 * A custom filter for processing JWT (JSON Web Token) authentication in a Spring Security-enabled
 * web application. This filter is responsible for extracting the JWT from the request, decoding and
 * verifying it, converting it into a UserPrincipal, and setting the authentication in the security context.
 *
 * Components:
 * - JwtDecoder: Component for decoding and verifying JWTs.
 * - JwtToPrincipalConverter: Component for converting a decoded JWT into a UserPrincipal.
 * - UserPrincipalAuthenticationToken: Represents an authentication token carrying UserPrincipal details.
 *
 * Extends:
 * - OncePerRequestFilter: Ensures that the filter is only executed once per request.
 *
 * Constructor:
 * - JwtAuthenticationFilter(JwtDecoder jwtDecoder, JwtToPrincipalConverter jwtToPrincipalConverter):
 *   Constructor with required dependencies injected.
 *
 * Method:
 * - doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain):
 *   Overrides the doFilterInternal method to perform the JWT authentication process. It extracts the
 *   token from the request, decodes and verifies it, converts it into a UserPrincipal, and sets the
 *   authentication in the SecurityContextHolder. The filter chain is then continued.
 *
 * - extractTokenFromRequest(HttpServletRequest request): Helper method to extract the JWT from the
 *   Authorization header in the request. It returns an Optional containing the token or an empty Optional
 *   if no valid token is found.
 *
 * Note: This filter assumes that JWTs are provided in the "Authorization" header with the format "Bearer <token>".
 *       Proper handling of exceptions, token validation issues, and potential improvements based on
 *       specific use cases should be implemented as needed.
 *
 * Usage: Register this filter in the Spring Security configuration to enable JWT authentication.
 */

