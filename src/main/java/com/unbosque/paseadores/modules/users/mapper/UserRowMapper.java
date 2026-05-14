package com.unbosque.paseadores.modules.users.mapper;

import com.unbosque.paseadores.modules.users.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .idUsuario(rs.getLong("id_usuario"))
                .correo(rs.getString("correo"))
                .contrasena(rs.getString("contrasena"))
                .telefono(rs.getString("telefono"))
                .primerNombre(rs.getString("primer_nombre"))
                .segundoNombre(rs.getString("segundo_nombre"))
                .primerApellido(rs.getString("primer_apellido"))
                .segundoApellido(rs.getString("segundo_apellido"))
                .fotoPerfil(rs.getString("foto_perfil"))
                .reputacion(rs.getDouble("reputacion"))
                .activo(rs.getBoolean("activo"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .roles(new ArrayList<>())
                .build();

    }
}
