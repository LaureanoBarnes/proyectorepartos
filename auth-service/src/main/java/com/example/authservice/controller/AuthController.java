package com.example.authservice.controller;

import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String userId = authService.register(request);
        return ResponseEntity.ok("Usuario registrado correctamente con ID: " + userId);
    }
}
