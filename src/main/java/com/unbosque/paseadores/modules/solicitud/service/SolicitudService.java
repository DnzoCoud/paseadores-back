package com.unbosque.paseadores.modules.solicitud.service;

import com.unbosque.paseadores.core.database.relational.service.RelationalDatabaseService;
import com.unbosque.paseadores.modules.events.model.EventType;
import com.unbosque.paseadores.modules.events.service.EventTrackingService;
import com.unbosque.paseadores.modules.solicitud.dto.CreateSolicitudRequest;
import com.unbosque.paseadores.modules.solicitud.dto.SolicitudEstadoResponseDto;
import com.unbosque.paseadores.modules.solicitud.dto.SolicitudResponseDto;
import com.unbosque.paseadores.modules.solicitud.mapper.SolicitudMapper;
import com.unbosque.paseadores.modules.solicitud.model.Solicitud;
import com.unbosque.paseadores.modules.solicitud.model.SolicitudEstado;
import com.unbosque.paseadores.modules.solicitud.repository.SolicitudRepository;
import com.unbosque.paseadores.modules.walktracking.service.GpsSimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SolicitudService {
    private final SolicitudRepository repository;
    private final SolicitudMapper mapper;
    private final RelationalDatabaseService dbService;
    private final EventTrackingService trackingService;
    private final GpsSimulationService gpsSimulationService;

    public SolicitudResponseDto create(
            Long idDueno,
            CreateSolicitudRequest request
    ) {
        return dbService.executeTransaction(() -> {
            Long idSolicitud =
                    repository.create(
                            idDueno,
                            request
                    );
            Solicitud solicitud =
                    repository.findById(
                            idSolicitud
                    );

            return mapper.toResponse(
                    solicitud
            );
        });
    }

    public SolicitudEstadoResponseDto aceptar(
            Long walkerId,
            Long requestId
    ) {

        return dbService.executeTransaction(() -> {
            Long paseoId = repository.aceptar(
                    walkerId,
                    requestId
            );

            gpsSimulationService.startTracking(paseoId);

            SolicitudEstado estado =
                    repository.findEstadoById(
                            requestId
                    );

            trackingService.track(
                    EventType.WALK_STARTED,
                    estado.getIdSolicitud(),
                    Map.of("fecha_ejecucion", LocalDateTime.now())
            );

            return SolicitudEstadoResponseDto.builder()
                    .idSolicitud(
                            estado.getIdSolicitud()
                    )
                    .estado(
                            estado.getEstado()
                    )
                    .mensaje(
                            "Solicitud aceptada correctamente"
                    )
                    .build();
        });
    }

    public SolicitudEstadoResponseDto rechazar(
            Long walkerId,
            Long requestId
    ) {

        return dbService.executeTransaction(() -> {

            repository.rechazar(

                    walkerId,
                    requestId
            );

            SolicitudEstado estado =
                    repository.findEstadoById(
                            requestId
                    );

            trackingService.track(
                    EventType.REQUEST_CANCELLED,
                    estado.getIdSolicitud(),
                    Map.of("fecha_ejecucion", LocalDateTime.now())
            );

            return SolicitudEstadoResponseDto.builder()
                    .idSolicitud(
                            estado.getIdSolicitud()
                    )
                    .estado(
                            estado.getEstado()
                    )
                    .mensaje(
                            "Solicitud rechazada correctamente"
                    )
                    .build();
        });
    }

    public SolicitudEstadoResponseDto cancelar(
            Long ownerId,
            Long requestId
    ) {

        return dbService.executeTransaction(() -> {

            repository.cancelar(

                    ownerId,
                    requestId
            );

            SolicitudEstado estado =
                    repository.findEstadoById(
                            requestId
                    );

            trackingService.track(
                    EventType.REQUEST_CANCELLED,
                    estado.getIdSolicitud(),
                    Map.of("fecha_ejecucion", LocalDateTime.now())
            );

            return SolicitudEstadoResponseDto.builder()
                    .idSolicitud(
                            estado.getIdSolicitud()
                    )
                    .estado(
                            estado.getEstado()
                    )
                    .mensaje(
                            "Solicitud cancelada correctamente"
                    )
                    .build();
        });
    }

    public List<SolicitudResponseDto> findByWalkerId(
            Long walkerId
    ) {

        return repository.findByWalkerId(
                        walkerId
                )
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
