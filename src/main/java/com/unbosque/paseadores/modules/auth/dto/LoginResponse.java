package com.unbosque.paseadores.modules.auth.dto;

import com.unbosque.paseadores.modules.users.dto.response.UserResponseDto;
import lombok.Builder;

@Builder
public record LoginResponse(
        String token,
        String type,
        UserResponseDto usuario
) { }