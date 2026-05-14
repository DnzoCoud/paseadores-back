package com.unbosque.paseadores.modules.users.mapper;

import com.unbosque.paseadores.modules.users.dto.response.RolResponseDto;
import com.unbosque.paseadores.modules.users.models.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {
    RolResponseDto toDto(Rol rol);
}
