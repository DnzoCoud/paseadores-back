package com.unbosque.paseadores.modules.paseo.mapper;

import com.unbosque.paseadores.modules.paseo.model.PaseoEstado;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaseoEstadoRowMapper
        implements RowMapper<PaseoEstado> {

    @Override
    public PaseoEstado mapRow(

            ResultSet rs,

            int rowNum

    ) throws SQLException {

        return PaseoEstado.builder()
                .idPaseo(
                        rs.getLong("id_paseo")
                )
                .estado(
                        rs.getString("estado")
                )
                .build();
    }
}