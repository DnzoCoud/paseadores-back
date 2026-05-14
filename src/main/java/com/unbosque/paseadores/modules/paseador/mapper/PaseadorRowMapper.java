package com.unbosque.paseadores.modules.paseador.mapper;

import com.unbosque.paseadores.modules.paseador.model.Paseador;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaseadorRowMapper implements RowMapper<Paseador> {

    @Override
    public Paseador mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        return Paseador.builder()
                .idUsuario(
                        rs.getLong("id_usuario")
                )
                .fechaRegistro(
                        rs.getTimestamp("fecha_registro")
                                .toLocalDateTime()
                )
                .descripcion(
                        rs.getString("descripcion")
                )
                .disponible(
                        rs.getBoolean("disponible")
                )
                .build();
    }
}
