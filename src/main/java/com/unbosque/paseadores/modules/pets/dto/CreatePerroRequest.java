package com.unbosque.paseadores.modules.pets.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreatePerroRequest(
        @NotBlank
        String nombre,
        String raza,
        @PositiveOrZero
        Integer edad,
        @DecimalMin("0.1")
        BigDecimal peso,
        String observaciones,
        String foto
) {
}
