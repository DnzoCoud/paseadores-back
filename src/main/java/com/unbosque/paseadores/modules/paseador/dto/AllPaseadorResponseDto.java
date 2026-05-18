package com.unbosque.paseadores.modules.paseador.dto;

import lombok.Builder;

@Builder
public record AllPaseadorResponseDto(
        Long idUsuario,
        String primerNombre,
        String primerApellido,
        String fotoPerfil,
        String descripcion,
        boolean disponible
) {
}
