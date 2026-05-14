package com.unbosque.paseadores.modules.paseador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterWalkerRequest(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no es válido")
        String correo,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 3, message = "La contraseña debe tener al menos 3 caracteres")
        String contrasena,
        @NotBlank(message = "El teléfono es obligatorio")
        String telefono,
        @NotBlank(message = "El primer nombre es obligatorio")
        String primerNombre,
        String segundoNombre,
        @NotBlank(message = "El primer apellido es obligatorio")
        String primerApellido,
        String segundoApellido,
        String fotoPerfil,
        String descripcion
) {
}