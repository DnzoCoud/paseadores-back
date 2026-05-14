package com.unbosque.paseadores.modules.users.dto.response;

import lombok.Builder;

@Builder
public record RolResponseDto(
        Long idRol,
        String nombre
) {
}