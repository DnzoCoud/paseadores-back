package com.unbosque.paseadores.modules.paseador.mapper;

import com.unbosque.paseadores.modules.paseador.model.AllPaseador;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AllPaseadorRowMapper  implements RowMapper<AllPaseador> {

    @Override
    public AllPaseador mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {
        return AllPaseador.builder()
                .idUsuario(
                        rs.getLong("id_usuario")
                )
                .primerNombre(
                        rs.getString("primer_nombre")
                )
                .primerApellido(
                        rs.getString("primer_apellido")
                )
                .fotoPerfil(
                        rs.getString("foto_perfil")
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
