package com.unbosque.paseadores.modules.paseador.service;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.events.model.EventType;
import com.unbosque.paseadores.modules.events.service.EventTrackingService;
import com.unbosque.paseadores.modules.paseador.dto.AllPaseadorResponseDto;
import com.unbosque.paseadores.modules.paseador.dto.PaseadorResponseDto;
import com.unbosque.paseadores.modules.paseador.dto.RegisterWalkerRequest;
import com.unbosque.paseadores.modules.paseador.mapper.AllPaseadorRowMapper;
import com.unbosque.paseadores.modules.paseador.mapper.PaseadorMapper;
import com.unbosque.paseadores.modules.paseador.model.Paseador;
import com.unbosque.paseadores.modules.paseador.repository.PaseadorRepository;
import com.unbosque.paseadores.modules.users.dto.request.RegisterUserRequest;
import com.unbosque.paseadores.modules.users.dto.response.UserResponseDto;
import com.unbosque.paseadores.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaseadorService {
    private final PaseadorRepository paseadorRepository;
    private final RelationalDatabaseService dbService;
    private final PaseadorMapper mapper;
    private final UserService userService;
    private final EventTrackingService trackingService;

    public PaseadorResponseDto register(RegisterWalkerRequest request) {
        return dbService.executeTransaction(() -> {
            RegisterUserRequest registerUserRequest =
                    RegisterUserRequest.builder()
                            .correo(request.correo())
                            .contrasena(request.contrasena())
                            .telefono(request.telefono())
                            .primerNombre(request.primerNombre())
                            .segundoNombre(request.segundoNombre())
                            .primerApellido(request.primerApellido())
                            .segundoApellido(request.segundoApellido())
                            .fotoPerfil(request.fotoPerfil())
                            .roles(List.of(2L))
                            .build();

            UserResponseDto user = userService.save(registerUserRequest);
            Paseador nuevoPaseador = Paseador.builder()
                    .idUsuario(user.idUsuario())
                    .descripcion(request.descripcion()).build();

            trackingService.track(
                    EventType.WALKER_REGISTERED,
                    nuevoPaseador.getIdUsuario(),
                    Map.of(
                            "fecha_registro",
                            nuevoPaseador.getFechaRegistro()
                    )
            );

            return mapper.toDto(paseadorRepository.save(nuevoPaseador));
        });
    }

    public List<AllPaseadorResponseDto> findAll() {
        trackingService.track(
                EventType.FIND_WALKERS,
                0L,
                Map.of("fecha_ejecucion", LocalDateTime.now())
        );

        return mapper.toResponseDtoList(
                paseadorRepository.findAll()
        );
    }
}
