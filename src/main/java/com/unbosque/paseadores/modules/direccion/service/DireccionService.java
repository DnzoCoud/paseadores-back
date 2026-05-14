package com.unbosque.paseadores.modules.direccion.service;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.direccion.dto.CreateDireccionRequest;
import com.unbosque.paseadores.modules.direccion.dto.DireccionResponseDto;
import com.unbosque.paseadores.modules.direccion.mapper.DireccionMapper;
import com.unbosque.paseadores.modules.direccion.model.Direccion;
import com.unbosque.paseadores.modules.direccion.repository.DireccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionService {
    private final DireccionRepository repository;
    private final DireccionMapper mapper;
    private final RelationalDatabaseService dbService;

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
