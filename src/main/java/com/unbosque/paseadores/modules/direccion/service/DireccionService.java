package com.unbosque.paseadores.modules.direccion.service;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.direccion.dto.CreateDireccionRequest;
import com.unbosque.paseadores.modules.direccion.dto.DireccionResponseDto;
import com.unbosque.paseadores.modules.direccion.mapper.DireccionMapper;
import com.unbosque.paseadores.modules.direccion.model.Direccion;
import com.unbosque.paseadores.modules.direccion.repository.DireccionRepository;
import com.unbosque.paseadores.modules.events.model.EventType;
import com.unbosque.paseadores.modules.events.service.EventTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DireccionService {
    private final DireccionRepository repository;
    private final DireccionMapper mapper;
    private final RelationalDatabaseService dbService;
    private final EventTrackingService trackingService;

    public DireccionResponseDto create(
            Long userId,
            CreateDireccionRequest request
    ) {
        return dbService.executeTransaction(() -> {
            Direccion direccion =
                    mapper.toModel(request);
            Direccion savedDireccion =
                    repository.create(
                            direccion
                    );

            trackingService.track(
                    EventType.REQUEST_CREATED,
                    savedDireccion.getIdDireccion(),
                    Map.of(
                            "direccion",
                            savedDireccion.getIdDireccion()
                    )
            );

            repository.createOwnerProfile(
                    userId,
                    savedDireccion.getIdDireccion()
            );
            return mapper.toResponse(
                    savedDireccion
            );
        });
    }

    public List<DireccionResponseDto> findByUserId(
            Long userId
    ) {
        return mapper.toResponseList(
                repository.findByUserId(userId)
        );
    }
}
