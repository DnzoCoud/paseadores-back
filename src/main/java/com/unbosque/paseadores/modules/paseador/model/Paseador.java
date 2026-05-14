package com.unbosque.paseadores.modules.paseador.model;

import com.unbosque.paseadores.modules.users.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paseador {
    private Long idUsuario;
    private LocalDateTime fechaRegistro;
    private String descripcion;
    private Boolean disponible;
    private User usuario;
}
