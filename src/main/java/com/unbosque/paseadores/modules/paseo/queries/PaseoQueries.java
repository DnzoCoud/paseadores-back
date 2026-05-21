package com.unbosque.paseadores.modules.paseo.queries;

public final class PaseoQueries {
    private PaseoQueries() {}

    public static final String FIND_BY_OWNER_ID = """
        SELECT DISTINCT
            p.id_paseo,
            p.estado,
            p.fecha_inicio,
            p.fecha_fin,
            p.precio,
            p.distancia_km,
            p.ruta,
            p.observaciones,
            p.id_solicitud,
            p.id_paseador,
            EXISTS (
                SELECT 1 FROM calificacion c
                WHERE c.id_paseo = p.id_paseo
                AND c.id_emisor = ?
            ) AS calificado
        FROM paseo p
        INNER JOIN paseo_perro pp ON pp.id_paseo = p.id_paseo
        INNER JOIN perro pe ON pe.id_perro = pp.id_perro
        WHERE pe.id_dueno = ?
        ORDER BY p.fecha_inicio DESC
    """;
    public static final String FIND_BY_WALKER_ID = """
        SELECT
            id_paseo,
            estado,
            fecha_inicio,
            fecha_fin,
            precio,
            distancia_km,
            ruta,
            observaciones,
            id_solicitud,
            id_paseador,
            false AS calificado
        FROM paseo
        WHERE id_paseador = ?
        ORDER BY fecha_inicio DESC
    """;

    public static final String FINALIZAR_PASEO = "CALL sp_finalizar_paseo(?, ?)";

    public static final String FIND_ESTADO_BY_ID = """
        SELECT
            id_paseo,
            estado
        FROM paseo
        WHERE id_paseo = ?
    """;

    public static final String CALIFICAR_PASEO = "CALL sp_calificar_paseo(?, ?, ?, ?)";

    public static final String PASEO_FINALIZADO = """
        SELECT estado = 'FINALIZADO'
        FROM paseo
        WHERE id_paseo = ?
    """;
}
