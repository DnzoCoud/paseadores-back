package com.unbosque.paseadores.modules.solicitud.mapper;

import com.unbosque.paseadores.modules.solicitud.model.SolicitudEstado;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SolicitudEstadoRowMapper
        implements RowMapper<SolicitudEstado> {

    @Override
    public SolicitudEstado mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        return SolicitudEstado.builder()
                .idSolicitud(
                        rs.getLong("id_solicitud")
                )
                .estado(
                        rs.getString("estado")
                )
                .build();
    }
}