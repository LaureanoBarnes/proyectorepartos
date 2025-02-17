package com.example.authservice.client;

import com.example.authservice.dto.UserCreateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// UserServiceClient.java
@FeignClient(
        name = "user-service",
        url = "${user-service.url}" // Usar propiedad configurable
)
public interface UserServiceClient {

    @PostMapping("/api/user")
    ResponseEntity<String> createUser(@RequestBody UserCreateDTO dto);
}

