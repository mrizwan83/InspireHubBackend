package com.rizzywebworks.InspireHub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String email;
    private String username;

 // For JSON serialization, but still hash and salt before storing
    private String password;

    private String role;
    private String extraInfo;
}
