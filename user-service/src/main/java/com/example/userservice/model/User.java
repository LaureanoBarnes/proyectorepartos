package com.example.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users") // Especifica la colección en MongoDB
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id; // MongoDB usa String para los IDs

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener menos de 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener menos de 50 caracteres")
    private String apellido;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    private String email;

    @NotNull(message = "El rol no puede estar vacío")
    private Rol rol;

    private Double latitud;
    private Double longitud;

    public User(String id, String nombre, String apellido, String email, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
        this.latitud = null;
        this.longitud = null;
    }

}