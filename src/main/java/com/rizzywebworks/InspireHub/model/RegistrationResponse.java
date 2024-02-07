package com.rizzywebworks.InspireHub.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String email;
    private String accessToken;
    private String errorMessage;

    // Constructor for successful registration
    public RegistrationResponse(String email, String accessToken) {
        this.email = email;
        this.accessToken = accessToken;
    }

    // Constructor for failed registration
    public RegistrationResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}