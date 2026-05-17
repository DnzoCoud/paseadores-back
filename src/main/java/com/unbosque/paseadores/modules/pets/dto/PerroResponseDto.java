package com.unbosque.paseadores.modules.pets.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PerroResponseDto(
        Long idPerro,
        String nombre,
        String raza,
        Integer edad,
        BigDecimal peso,
        String observaciones,
        String foto,
        Long idDueno
) {
}
