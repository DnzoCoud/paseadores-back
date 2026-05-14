package com.unbosque.paseadores.modules.users.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long idUsuario;
    private String correo;
    private String contrasena;
    private String telefono;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String fotoPerfil;
    private double reputacion;
    private boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Rol> roles;
}
