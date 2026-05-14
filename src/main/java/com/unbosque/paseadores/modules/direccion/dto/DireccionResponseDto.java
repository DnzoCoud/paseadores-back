package com.unbosque.paseadores.modules.direccion.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DireccionResponseDto(
        Long idDireccion,
        String detalle,
        String barrio,
        String ciudad,
        BigDecimal latitud,
        BigDecimal longitud
) {}