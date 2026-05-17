package com.unbosque.paseadores.modules.pets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perro {
    private Long idPerro;
    private String nombre;
    private String raza;
    private Integer edad;
    private BigDecimal peso;
    private String observaciones;
    private String foto;
    private Long idDueno;
    private boolean activo;
}