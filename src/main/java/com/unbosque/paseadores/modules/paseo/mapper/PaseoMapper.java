package com.unbosque.paseadores.modules.paseo.mapper;

import com.unbosque.paseadores.modules.paseo.dto.PaseoResponseDto;
import com.unbosque.paseadores.modules.paseo.model.Paseo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaseoMapper {

    PaseoResponseDto toResponse(
            Paseo paseo
    );

    List<PaseoResponseDto> toResponseList(
            List<Paseo> paseos
    );
}
