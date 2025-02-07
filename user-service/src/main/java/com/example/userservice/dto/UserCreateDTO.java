package com.example.userservice.dto;

import com.example.userservice.model.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    @NotBlank
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Schema(description = "Rol del usuario", example = "CLIENTE", allowableValues = {"CLIENTE", "REPARTIDOR", "ADMIN"})
    private Rol rol;
}

