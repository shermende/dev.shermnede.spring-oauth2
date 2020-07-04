package dev.shermende.resource.configuration.properties;

import lombok.Data;

@Data
public class OAuthClientProperties {
    private String client = "client";
    private String secret = "secret";
    private String url = "http://localhost:8081/oauth/check_token";
}