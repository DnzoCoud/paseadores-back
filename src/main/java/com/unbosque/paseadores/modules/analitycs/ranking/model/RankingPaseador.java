package com.unbosque.paseadores.modules.analitycs.ranking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingPaseador {

    private Long idUsuario;

    private String primerNombre;

    private BigDecimal reputacion;

    private Integer ranking;
}