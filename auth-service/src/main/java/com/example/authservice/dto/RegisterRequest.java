package com.example.authservice.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String rol; // Campo para el rol seleccionado
}

