package com.example.authservice.config;

import com.example.authservice.service.TokenService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private final TokenService tokenService;

    public FeignClientInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = tokenService.getAdminToken();
        template.header("Authorization", "Bearer " + token);
    }
}

