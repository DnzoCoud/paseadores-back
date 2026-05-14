package com.unbosque.paseadores.modules.paseador.dto;

import com.unbosque.paseadores.modules.users.dto.response.UserResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PaseadorResponseDto(
    Long idUsuario,
    LocalDateTime fechaRegistro,
    String descripcion,
    Boolean disponible,
    UserResponseDto usuario
) {
}
