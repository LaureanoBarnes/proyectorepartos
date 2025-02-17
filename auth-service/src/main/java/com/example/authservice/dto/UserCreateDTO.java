package com.example.authservice.dto;

// En auth-service (debe coincidir con user-service)


import lombok.Data;

@Data
public class UserCreateDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String rol; // Asegúrate de que coincida con el enum de user-service

}
