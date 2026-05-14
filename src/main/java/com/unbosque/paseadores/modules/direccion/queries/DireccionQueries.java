package com.unbosque.paseadores.modules.direccion.queries;

public final class DireccionQueries {

    private DireccionQueries() {}

    public static final String CREATE_DIRECCION = """
        INSERT INTO direccion(
            detalle,
            barrio,
            ciudad,
            latitud,
            longitud
        )
        VALUES (
            ?, ?, ?, ?, ?
        )
        RETURNING *
    """;

    public static final String CREATE_DUENO = """
        INSERT INTO dueno(
            id_usuario,
            id_direccion
        )
        VALUES (
            ?, ?
        )
    """;

    public static final String FIND_BY_USER_ID = """
        SELECT d.*
        FROM direccion d
        INNER JOIN dueno du
            ON du.id_direccion = d.id_direccion
        WHERE du.id_usuario = ?
    """;
}
