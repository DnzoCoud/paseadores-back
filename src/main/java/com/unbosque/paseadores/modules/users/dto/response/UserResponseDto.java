package com.unbosque.paseadores.modules.users.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record UserResponseDto(
        Long idUsuario,
        String correo,
        String telefono,
        String primerNombre,
        String segundoNombre,
        String primerApellido,
        String segundoApellido,
        String fotoPerfil,
        double reputacion,
        boolean activo,
        List<RolResponseDto> roles
) {
}