package com.example.authservice.service;

import com.example.authservice.client.UserServiceClient;
import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.dto.UserCreateDTO;
import com.example.authservice.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor // Inyecta automáticamente las dependencias
public class AuthService {

    private final IKeycloackService keycloakService; // Inyecta KeycloakService
    private final UserServiceClient userServiceClient;

    public String register(RegisterRequest request) {
        // Transformación del rol del frontend al rol del realm de Keycloak
        String realmRole;
        switch (request.getRol()) {
            case "cliente_client_role":
                realmRole = "cliente";
                break;
            case "repartidor_client_role":
                realmRole = "repartidor";
                break;
            case "admin_client_role":
                realmRole = "admin";
                break;
            default:
                realmRole = "cliente"; // Por defecto, se asigna "cliente"
        }

        // 1. Crear usuario en Keycloak
        UserDTO userDTO = UserDTO.builder()
                .username(request.getEmail())
                .email(request.getEmail())
                .firstname(request.getNombre())
                .lastname(request.getApellido())
                .password(request.getPassword())
                .roles(Set.of(realmRole)) // Se asigna el rol transformado
                .build();

        String keycloakUserId = keycloakService.createUser(userDTO); // Crea el usuario en Keycloak y asigna roles

        // 2. Enviar datos a user-service para guardar en MongoDB
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setNombre(request.getNombre());
        userCreateDTO.setApellido(request.getApellido());
        userCreateDTO.setEmail(request.getEmail());
        userCreateDTO.setRol(realmRole); // Se asigna el mismo rol transformado

        ResponseEntity<String> response = userServiceClient.createUser(userCreateDTO);
        return "Usuario creado con ID: " + keycloakUserId;
    }


}