package com.afkl.travel.exercise.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for test client.
 */
@Configuration
public class ClientConfiguration {

    @Value("${rest.baseurl}")
    private String baseUrl;

    @Value("${rest.defaultUsername}")
    private String username;

    @Value("${rest.defaultPassword}")
    private String password;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
