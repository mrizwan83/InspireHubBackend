package com.rizzywebworks.InspireHub.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    // Other fields if needed
}
