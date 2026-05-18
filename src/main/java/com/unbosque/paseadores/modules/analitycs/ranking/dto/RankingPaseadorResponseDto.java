package com.unbosque.paseadores.modules.analitycs.ranking.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RankingPaseadorResponseDto(

        Long idUsuario,

        String primerNombre,

        BigDecimal reputacion,

        Integer ranking
) {
}