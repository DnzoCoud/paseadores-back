package com.unbosque.paseadores.modules.paseo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record CreateCalificacionRequest(
        @PositiveOrZero
        Integer puntaje,
        String comentario
) {}