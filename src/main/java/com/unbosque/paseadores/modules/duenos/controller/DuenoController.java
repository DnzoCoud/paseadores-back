package com.unbosque.paseadores.modules.duenos.controller;

import com.unbosque.paseadores.core.handlers.ApiResponse;
import com.unbosque.paseadores.modules.paseo.dto.CreateCalificacionRequest;
import com.unbosque.paseadores.modules.paseo.dto.PaseoResponseDto;
import com.unbosque.paseadores.modules.paseo.services.PaseoService;
import com.unbosque.paseadores.modules.pets.dto.CreatePerroRequest;
import com.unbosque.paseadores.modules.pets.dto.PerroResponseDto;
import com.unbosque.paseadores.modules.pets.service.PerroService;
import com.unbosque.paseadores.modules.solicitud.dto.CreateSolicitudRequest;
import com.unbosque.paseadores.modules.solicitud.dto.SolicitudEstadoResponseDto;
import com.unbosque.paseadores.modules.solicitud.service.SolicitudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class DuenoController {
    private final PerroService perroService;
    private final SolicitudService solicitudService;
    private final PaseoService paseoService;

    @PostMapping("/{ownerId}/pets")
    public ResponseEntity<ApiResponse<?>> create(
            @PathVariable
            Long ownerId,
            @RequestBody
            @Valid
            CreatePerroRequest request
    ) {

        var response = perroService.create(
                ownerId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }

    @GetMapping("/{ownerId}/pets")
    public ResponseEntity<ApiResponse<List<PerroResponseDto>>> findPetsByOwnerId(
            @PathVariable
            Long ownerId
    ) {
        var response = perroService.findByOwnerId(ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @DeleteMapping("/{ownerId}/pets/{perroId}")
    public ResponseEntity<?> deletePetByOwnerIdAndPerroId(
            @PathVariable
            Long ownerId,
            @PathVariable
            Long perroId
    ) {
        perroService.deleteByOwnerId(ownerId, perroId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{ownerId}/requests")
    public ResponseEntity<ApiResponse<?>> create(
            @PathVariable
            Long ownerId,
            @RequestBody
            @Valid
            CreateSolicitudRequest request
    ) {
        var response = solicitudService.create(ownerId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }

    @GetMapping("/{ownerId}/requests")
    public ResponseEntity<ApiResponse<?>> findRequestsByOwnerId(
            @PathVariable
            Long ownerId
    ) {
        var response = solicitudService.findByOwnerId(ownerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(response));
    }

    @PatchMapping("/{ownerId}/requests/{requestId}/cancel")
    public ResponseEntity<ApiResponse<SolicitudEstadoResponseDto>> cancelar(
            @PathVariable
            Long ownerId,
            @PathVariable
            Long requestId
    ) {
        var response = solicitudService.cancelar(ownerId, requestId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @GetMapping("/{ownerId}/walks")
    public ResponseEntity<ApiResponse<List<PaseoResponseDto>>> findPaseoByOwnerIdId(
            @PathVariable
            Long ownerId
    ) {
        var response = paseoService.findByOwnerId(ownerId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @PostMapping("/{ownerId}/walks/{walkId}/ratings")
    public ResponseEntity<Void> calificar(
            @PathVariable
            Long ownerId,
            @PathVariable
            Long walkId,
            @RequestBody
            @Valid
            CreateCalificacionRequest request

    ) {
        paseoService.calificar(
                walkId,
                ownerId,
                request
        );

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).build();
    }
}
