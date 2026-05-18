package com.unbosque.paseadores.modules.paseador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllPaseador {
    private Long idUsuario;
    private String primerNombre;
    private String primerApellido;
    private String fotoPerfil;
    private String descripcion;
    private boolean disponible;
}
