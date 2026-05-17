package com.unbosque.paseadores.modules.solicitud.mapper;

import com.unbosque.paseadores.modules.solicitud.model.Solicitud;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SolicitudRowMapper
        implements RowMapper<Solicitud> {

    @Override
    public Solicitud mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        return Solicitud.builder()
                .idSolicitud(
                        rs.getLong("id_solicitud")
                )
                .fechaSolicitud(
                        rs.getTimestamp("fecha_solicitud")
                                .toLocalDateTime()
                )
                .estado(
                        rs.getString("estado")
                )
                .horaSugerida(
                        rs.getTime("hora_sugerida")
                                .toLocalTime()
                )
                .puntoEncuentro(
                        rs.getString("punto_encuentro")
                )
                .cantidadPerros(
                        rs.getInt("cantidad_perros")
                )
                .observaciones(
                        rs.getString("observaciones")
                )
                .idDueno(
                        rs.getLong("id_dueno")
                )
                .idPaseador(
                        rs.getLong("id_paseador")
                )
                .build();
    }
}
