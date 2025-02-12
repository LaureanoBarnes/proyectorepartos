package com.example.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/protected-endpoint")
    @PreAuthorize("hasRole('admin_client_role')")
    public String protectedEndpoint() {
        return "¡Este es un endpoint protegido!";

    }
    @GetMapping("/public-endpoint")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("¡Este es un endpoint público!");
    }
}