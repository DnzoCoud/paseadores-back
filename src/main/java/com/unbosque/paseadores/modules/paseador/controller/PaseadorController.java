package com.unbosque.paseadores.modules.paseador.controller;
import com.unbosque.paseadores.core.handlers.ApiResponse;
import com.unbosque.paseadores.modules.paseador.service.PaseadorService;
import com.unbosque.paseadores.modules.paseo.dto.PaseoEstadoResponseDto;
import com.unbosque.paseadores.modules.paseo.dto.PaseoResponseDto;
import com.unbosque.paseadores.modules.paseo.services.PaseoService;
import com.unbosque.paseadores.modules.solicitud.dto.SolicitudEstadoResponseDto;
import com.unbosque.paseadores.modules.solicitud.dto.SolicitudResponseDto;
import com.unbosque.paseadores.modules.solicitud.service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paseador/")
@RequiredArgsConstructor
public class PaseadorController {
    private final SolicitudService solicitudService;
    private final PaseoService paseoService;
    private final PaseadorService paseadorService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> findAll() {
        var response = paseadorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @GetMapping("/{walkerId}/requests")
    public ResponseEntity<ApiResponse<?>> findByWalkerId(
            @PathVariable
            Long walkerId
    ) {
        var response = solicitudService.findByWalkerId(walkerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @PatchMapping("/{walkerId}/requests/{requestId}/accept")
    public ResponseEntity<ApiResponse<SolicitudEstadoResponseDto>> aceptar(
            @PathVariable
            Long walkerId,
            @PathVariable
            Long requestId
    ) {
        var response = solicitudService.aceptar(walkerId, requestId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @PatchMapping("/{walkerId}/requests/{requestId}/reject")
    public ResponseEntity<ApiResponse<SolicitudEstadoResponseDto>> rechazar(
            @PathVariable
            Long walkerId,
            @PathVariable
            Long requestId

    ) {

        var response = solicitudService.rechazar(walkerId, requestId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @GetMapping("/{walkerId}/walks")
    public ResponseEntity<ApiResponse<List<PaseoResponseDto>>> findPaseoByWalkerId(
            @PathVariable
            Long walkerId
    ) {
        var response = paseoService.findByWalkerId(walkerId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @PatchMapping("/{walkerId}/walks/{walkId}/finish")
    public ResponseEntity<PaseoEstadoResponseDto> finalizar(
            @PathVariable
            Long walkerId,
            @PathVariable
            Long walkId
    ) {

        return ResponseEntity.ok(
                paseoService.finalizar(
                        walkId,
                        walkerId
                )
        );
    }
}
