package com.unbosque.paseadores.modules.users.mapper;

import com.unbosque.paseadores.modules.users.models.Rol;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RolRowMapper implements RowMapper<Rol> {

    @Override
    public Rol mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Rol.builder()
                .idRol(rs.getLong("id_rol"))
                .nombre(rs.getString("nombre"))
                .build();
    }
}
