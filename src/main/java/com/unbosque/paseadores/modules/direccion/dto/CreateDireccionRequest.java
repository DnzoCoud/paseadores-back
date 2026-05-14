package com.unbosque.paseadores.modules.direccion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateDireccionRequest(
        @NotBlank
        String detalle,
        String barrio,
        String ciudad,
        BigDecimal latitud,
        BigDecimal longitud
) {
}
