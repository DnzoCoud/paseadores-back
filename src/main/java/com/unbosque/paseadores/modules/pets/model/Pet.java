package com.unbosque.paseadores.modules.pets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private Long idPerro;
    private String nombre;
    private String raza;
    private int edad;
    private int peso;
    private String observaciones;
    private String foto;
}
