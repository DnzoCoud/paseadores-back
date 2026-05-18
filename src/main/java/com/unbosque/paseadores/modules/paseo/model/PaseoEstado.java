package com.unbosque.paseadores.modules.paseo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaseoEstado {
    private Long idPaseo;
    private String estado;
}