package com.unbosque.paseadores.modules.users.mapper;

import com.unbosque.paseadores.modules.users.dto.request.RegisterUserRequest;
import com.unbosque.paseadores.modules.users.dto.response.UserResponseDto;
import com.unbosque.paseadores.modules.users.models.Rol;
import com.unbosque.paseadores.modules.users.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = { RolMapper.class }
)
public interface UserMapper {
    UserResponseDto toDto(User user);
    User registerToModel(RegisterUserRequest registerUserRequest);

    default List<Rol> toRoles(
            List<Long> roles
    ) {

        if (roles == null) {
            return List.of();
        }

        return roles.stream()
                .map(id -> Rol.builder()
                        .idRol(id)
                        .build())
                .toList();
    }
}
