package com.example.userservice.dto;

import com.example.userservice.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;
    private Double latitud;
    private Double longitud;
}