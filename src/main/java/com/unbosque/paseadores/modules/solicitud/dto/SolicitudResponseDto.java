package com.unbosque.paseadores.modules.solicitud.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record SolicitudResponseDto(

        Long idSolicitud,

        LocalDateTime fechaSolicitud,

        String estado,

        LocalTime horaSugerida,

        String puntoEncuentro,

        Integer cantidadPerros,

        String observaciones,

        Long idDueno,

        Long idPaseador
) {
}
