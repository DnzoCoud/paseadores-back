package com.unbosque.paseadores.modules.pets.mapper;

import com.unbosque.paseadores.modules.pets.dto.CreatePerroRequest;
import com.unbosque.paseadores.modules.pets.dto.PerroResponseDto;
import com.unbosque.paseadores.modules.pets.model.Perro;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PerroMapper {

    Perro toModel(
            CreatePerroRequest request
    );

    PerroResponseDto toResponse(
            Perro perro
    );

    List<PerroResponseDto> toResponseList(
            List<Perro> perros
    );
}
