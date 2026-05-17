package com.unbosque.paseadores.modules.pets.mapper;

import com.unbosque.paseadores.modules.pets.model.Perro;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PerroRowMapper
        implements RowMapper<Perro> {

    @Override
    public Perro mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        return Perro.builder()
                .idPerro(
                        rs.getLong("id_perro")
                )
                .nombre(
                        rs.getString("nombre")
                )
                .raza(
                        rs.getString("raza")
                )
                .edad(
                        rs.getInt("edad")
                )
                .peso(
                        rs.getBigDecimal("peso")
                )
                .observaciones(
                        rs.getString("observaciones")
                )
                .foto(
                        rs.getString("foto")
                )
                .idDueno(
                        rs.getLong("id_dueno")
                )
                .activo(
                        rs.getBoolean("activo")
                )
                .build();
    }
}