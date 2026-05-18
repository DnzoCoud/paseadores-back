package com.unbosque.paseadores.modules.paseador.queries;

public final class PaseadorQueries {
    public static final String REGISTRAR_PASEADOR = """
        SELECT *
        FROM fn_crear_paseador(
            ?, ?
        )
    """;

    public static final String FIND_ALL = """
        SELECT
            p.id_usuario,
            u.primer_nombre,
            u.primer_apellido,
            u.foto_perfil,
            u.reputacion,
            p.descripcion,
            p.disponible
        FROM paseador p
        INNER JOIN usuario u
            ON u.id_usuario = p.id_usuario
        WHERE p.disponible = true
        ORDER BY u.reputacion DESC
    """;
}
