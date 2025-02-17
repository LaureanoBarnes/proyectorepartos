package com.example.authservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TokenService {

    @Value("${keycloak.token-url}") // Ejemplo: http://localhost:8081/realms/delivery-system/protocol/openid-connect/token
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    private final WebClient webClient = WebClient.create();

    public String getAdminToken() {
        return webClient.post()
                .uri(tokenUrl)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block()
                .getAccessToken();
    }

    public static class TokenResponse {
        private String access_token;
        private int expires_in;
        // Getters y Setters
        public String getAccessToken() { return access_token; }
        public void setAccess_token(String access_token) { this.access_token = access_token; }
        public int getExpires_in() { return expires_in; }
        public void setExpires_in(int expires_in) { this.expires_in = expires_in; }
    }
}

