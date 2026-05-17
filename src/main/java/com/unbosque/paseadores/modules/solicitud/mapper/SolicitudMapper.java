package com.unbosque.paseadores.modules.solicitud.mapper;

import com.unbosque.paseadores.modules.solicitud.dto.SolicitudResponseDto;
import com.unbosque.paseadores.modules.solicitud.model.Solicitud;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SolicitudMapper {
    SolicitudResponseDto toResponse(
            Solicitud solicitud
    );
}
