package com.rizzywebworks.InspireHub.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    /**
     * Secret Key used for issuing JWT
     */
    private String secretKey;
}


//this allows us to actually configure secretKey on our application properties or yaml
//and change it depending on the environment