package com.unbosque.paseadores.modules.paseador.mapper;

import com.unbosque.paseadores.modules.paseador.dto.PaseadorResponseDto;
import com.unbosque.paseadores.modules.paseador.model.Paseador;
import com.unbosque.paseadores.modules.users.mapper.UserMapper;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = { UserMapper.class }
)

public interface PaseadorMapper {
    PaseadorResponseDto toDto(Paseador paseador);
}
