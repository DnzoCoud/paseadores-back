package com.unbosque.paseadores.modules.solicitud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

@Builder
public record CreateSolicitudRequest(
        @NotNull
        LocalTime horaSugerida,
        @NotBlank
        String puntoEncuentro,
        String observaciones,
        @NotNull
        Long idPaseador,
        @NotEmpty
        List<Long> perros
) {
}
