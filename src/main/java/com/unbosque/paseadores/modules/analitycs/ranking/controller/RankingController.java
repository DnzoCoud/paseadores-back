package com.unbosque.paseadores.modules.analitycs.ranking.controller;

import com.unbosque.paseadores.core.handlers.ApiResponse;
import com.unbosque.paseadores.modules.analitycs.ranking.dto.RankingPaseadorResponseDto;
import com.unbosque.paseadores.modules.analitycs.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rankings/walkers")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RankingPaseadorResponseDto>>> findAll() {
        var rankings = service.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(rankings));
    }

    @GetMapping("/top-10")
    public ResponseEntity<ApiResponse<List<RankingPaseadorResponseDto>>> findTop10() {
        var rankings = service.findTop10();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(rankings));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<RankingPaseadorResponseDto>> findById(
            @PathVariable
            Long userId
    ) {
        var result = service.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(result));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshRanking() {

        service.refreshRanking();
        return ResponseEntity.noContent().build();
    }
}
