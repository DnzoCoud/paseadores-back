package com.unbosque.paseadores.modules.solicitud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    private Long idSolicitud;
    private LocalDateTime fechaSolicitud;
    private String estado;
    private LocalTime horaSugerida;
    private String puntoEncuentro;
    private Integer cantidadPerros;
    private String observaciones;
    private Long idDueno;
    private Long idPaseador;
}