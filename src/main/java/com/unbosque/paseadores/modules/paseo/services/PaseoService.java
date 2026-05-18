package com.unbosque.paseadores.modules.paseo.services;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.paseo.dto.CreateCalificacionRequest;
import com.unbosque.paseadores.modules.paseo.dto.PaseoEstadoResponseDto;
import com.unbosque.paseadores.modules.paseo.dto.PaseoResponseDto;
import com.unbosque.paseadores.modules.paseo.mapper.PaseoMapper;
import com.unbosque.paseadores.modules.paseo.model.PaseoEstado;
import com.unbosque.paseadores.modules.paseo.repository.PaseoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaseoService {
    private final PaseoRepository repository;
    private final PaseoMapper mapper;
    private final RelationalDatabaseService dbService;

    public List<PaseoResponseDto> findByOwnerId(
            Long ownerId
    ) {

        return mapper.toResponseList(
                repository.findByOwnerId(
                        ownerId
                )
        );
    }

    public List<PaseoResponseDto> findByWalkerId(
            Long walkerId
    ) {

        return mapper.toResponseList(
                repository.findByWalkerId(
                        walkerId
                )
        );
    }

    public PaseoEstadoResponseDto finalizar(
            Long paseoId,
            Long walkerId

    ) {

        return dbService.executeTransaction(() -> {

            repository.finalizar(

                    paseoId,
                    walkerId
            );

            PaseoEstado estado =
                    repository.findEstadoById(
                            paseoId
                    );

            return PaseoEstadoResponseDto.builder()
                    .idPaseo(
                            estado.getIdPaseo()
                    )
                    .estado(
                            estado.getEstado()
                    )
                    .mensaje(
                            "Paseo finalizado correctamente"
                    )
                    .build();
        });
    }

    public void calificar(
            Long paseoId,
            Long ownerId,
            CreateCalificacionRequest request

    ) {
        dbService.executeTransaction(() -> {
            repository.calificar(
                    paseoId,
                    ownerId,
                    request
            );
            return null;
        });
    }
}
