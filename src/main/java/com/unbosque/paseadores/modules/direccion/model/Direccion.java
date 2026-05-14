package com.unbosque.paseadores.modules.direccion.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private Long idDireccion;
    private String detalle;
    private String barrio;
    private String ciudad;
    private BigDecimal latitud;
    private BigDecimal longitud;
}