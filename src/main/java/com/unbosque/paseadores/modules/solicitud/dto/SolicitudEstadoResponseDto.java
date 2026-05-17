package com.unbosque.paseadores.modules.solicitud.dto;

import lombok.Builder;

@Builder
public record SolicitudEstadoResponseDto(
        Long idSolicitud,
        String estado,
        String mensaje
) {
}
