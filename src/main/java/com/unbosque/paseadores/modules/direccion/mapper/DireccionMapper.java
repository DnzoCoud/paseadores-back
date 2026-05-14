package com.unbosque.paseadores.modules.direccion.mapper;

import com.unbosque.paseadores.modules.direccion.dto.CreateDireccionRequest;
import com.unbosque.paseadores.modules.direccion.dto.DireccionResponseDto;
import com.unbosque.paseadores.modules.direccion.model.Direccion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DireccionMapper {
    Direccion toModel(
            CreateDireccionRequest request
    );

    DireccionResponseDto toResponse(
            Direccion direccion
    );

    List<DireccionResponseDto> toResponseList(
            List<Direccion> direcciones
    );
}
