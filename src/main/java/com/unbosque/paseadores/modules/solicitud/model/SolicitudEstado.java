package com.unbosque.paseadores.modules.solicitud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudEstado {
    private Long idSolicitud;
    private String estado;
}
