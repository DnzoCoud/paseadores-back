package com.unbosque.paseadores.modules.direccion.controller;

import com.unbosque.paseadores.core.handlers.ApiResponse;
import com.unbosque.paseadores.modules.direccion.dto.CreateDireccionRequest;
import com.unbosque.paseadores.modules.direccion.service.DireccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/owners/{userId}/direcciones")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService service;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            @PathVariable
            Long userId,
            @RequestBody
            @Valid
            CreateDireccionRequest request

    ) {
        var resp = service.create(
                userId,
                request
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(resp));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> findByUserId(
            @PathVariable
            Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(service.findByUserId(userId)));
    }
}