package com.unbosque.paseadores.modules.paseo.dto;

import lombok.Builder;

@Builder
public record PaseoEstadoResponseDto(

        Long idPaseo,

        String estado,

        String mensaje
) {
}
