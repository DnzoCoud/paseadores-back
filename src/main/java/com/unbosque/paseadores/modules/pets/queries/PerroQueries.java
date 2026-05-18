package com.unbosque.paseadores.modules.pets.queries;

public final class PerroQueries {
    private PerroQueries() {}

    public static final String CREATE_PERRO = """
        INSERT INTO perro(
            nombre,
            raza,
            edad,
            peso,
            observaciones,
            foto,
            id_dueno,
            activo
        )
        VALUES (
            ?, ?, ?, ?, ?, ?, ?, ?
        )
        RETURNING *
    """;

    public static final String FIND_BY_OWNER_ID = """
        SELECT
            id_perro,
            nombre,
            raza,
            edad,
            peso,
            observaciones,
            foto,
            id_dueno,
            activo
        FROM perro
        WHERE id_dueno = ?
        AND activo = true
        ORDER BY nombre ASC
    """;

    public static final String DELETE = """
        UPDATE perro
        SET activo = false
        WHERE id_perro = ?
        AND id_dueno = ?
    """;

    public static final String EXISTS_BY_ID_AND_OWNER = """
        SELECT EXISTS(
            SELECT 1
            FROM perro
            WHERE id_perro = ?
            AND id_dueno = ?
            AND activo = true
        )
    """;
}
