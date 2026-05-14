package com.unbosque.paseadores.modules.auth.controller;

import com.unbosque.paseadores.core.handlers.ApiResponse;
import com.unbosque.paseadores.modules.auth.dto.LoginRequest;
import com.unbosque.paseadores.modules.auth.dto.LoginResponse;
import com.unbosque.paseadores.modules.auth.services.AuthService;
import com.unbosque.paseadores.modules.paseador.dto.PaseadorResponseDto;
import com.unbosque.paseadores.modules.paseador.dto.RegisterWalkerRequest;
import com.unbosque.paseadores.modules.paseador.service.PaseadorService;
import com.unbosque.paseadores.modules.users.dto.request.RegisterUserRequest;
import com.unbosque.paseadores.modules.users.dto.response.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PaseadorService paseadorService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody RegisterUserRequest request) {
        UserResponseDto response = authService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(response));
    }

    @PostMapping("/paseador")
    public ResponseEntity<ApiResponse<PaseadorResponseDto>> register(@Valid @RequestBody RegisterWalkerRequest request) {
        var response = paseadorService.register(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
