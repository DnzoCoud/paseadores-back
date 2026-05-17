package com.unbosque.paseadores.modules.paseo.mapper;

import com.unbosque.paseadores.modules.paseo.model.Paseo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class PaseoRowMapper
        implements RowMapper<Paseo> {

    @Override
    public Paseo mapRow(

            ResultSet rs,

            int rowNum

    ) throws SQLException {

        Timestamp fechaFin =
                rs.getTimestamp("fecha_fin");

        return Paseo.builder()
                .idPaseo(
                        rs.getLong("id_paseo")
                )
                .estado(
                        rs.getString("estado")
                )
                .fechaInicio(
                        rs.getTimestamp("fecha_inicio")
                                .toLocalDateTime()
                )
                .fechaFin(
                        fechaFin != null
                                ? fechaFin.toLocalDateTime()
                                : null
                )
                .precio(
                        rs.getBigDecimal("precio")
                )
                .distanciaKm(
                        rs.getBigDecimal("distancia_km")
                )
                .ruta(
                        rs.getString("ruta")
                )
                .observaciones(
                        rs.getString("observaciones")
                )
                .idSolicitud(
                        rs.getLong("id_solicitud")
                )
                .idPaseador(
                        rs.getLong("id_paseador")
                )
                .build();
    }
}
