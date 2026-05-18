package com.unbosque.paseadores.modules.paseo.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaseoResponseDto(
        Long idPaseo,
        String estado,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        BigDecimal precio,
        BigDecimal distanciaKm,
        String ruta,
        String observaciones,
        Long idSolicitud,
        Long idPaseador
) {
}