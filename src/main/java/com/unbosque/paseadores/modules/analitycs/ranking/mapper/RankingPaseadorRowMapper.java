package com.unbosque.paseadores.modules.analitycs.ranking.mapper;

import com.unbosque.paseadores.modules.analitycs.ranking.model.RankingPaseador;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RankingPaseadorRowMapper
        implements RowMapper<RankingPaseador> {

    @Override
    public RankingPaseador mapRow(
            ResultSet rs,
            int rowNum

    ) throws SQLException {
        return RankingPaseador.builder()
                .idUsuario(
                        rs.getLong("id_usuario")
                )
                .primerNombre(
                        rs.getString("primer_nombre")
                )
                .reputacion(
                        rs.getBigDecimal("reputacion")
                )
                .ranking(
                        rs.getInt("ranking")
                )
                .build();
    }
}