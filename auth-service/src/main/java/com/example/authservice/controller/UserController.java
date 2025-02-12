package com.example.authservice.controller;

import com.example.authservice.dto.UserDTO;
import com.example.authservice.service.IKeycloackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IKeycloackService keycloakService;

    @PostMapping
    @PreAuthorize("hasRole('admin_client_role')")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        String userId = keycloakService.createUser(userDTO);
        return ResponseEntity.ok("Usuario creado con ID: " + userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('admin_client_role')")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(keycloakService.findAllUsers());
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('admin_client_role')")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        keycloakService.deleteUser(userId);
        return ResponseEntity.ok("Usuario eliminado");
    }
}