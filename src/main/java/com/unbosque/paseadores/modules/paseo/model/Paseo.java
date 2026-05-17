package com.unbosque.paseadores.modules.paseo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paseo {

    private Long idPaseo;

    private String estado;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private BigDecimal precio;

    private BigDecimal distanciaKm;

    private String ruta;

    private String observaciones;

    private Long idSolicitud;

    private Long idPaseador;
}