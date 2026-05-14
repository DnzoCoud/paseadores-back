package com.unbosque.paseadores.modules.direccion.mapper;

import com.unbosque.paseadores.modules.direccion.model.Direccion;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DireccionRowMapper
        implements RowMapper<Direccion> {

    @Override
    public Direccion mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        return Direccion.builder()
                .idDireccion(
                        rs.getLong("id_direccion")
                )
                .detalle(
                        rs.getString("detalle")
                )
                .barrio(
                        rs.getString("barrio")
                )
                .ciudad(
                        rs.getString("ciudad")
                )
                .latitud(
                        rs.getBigDecimal("latitud")
                )
                .longitud(
                        rs.getBigDecimal("longitud")
                )
                .build();
    }
}