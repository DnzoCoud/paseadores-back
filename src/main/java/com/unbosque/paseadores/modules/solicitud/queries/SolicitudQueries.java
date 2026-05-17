package com.unbosque.paseadores.modules.solicitud.queries;

public class SolicitudQueries {
    private SolicitudQueries() {}

    public static final String FIND_BY_ID = """
        SELECT
            id_solicitud,
            fecha_solicitud,
            estado,
            hora_sugerida,
            punto_encuentro,
            cantidad_perros,
            observaciones,
            id_dueno,
            id_paseador
        FROM solicitud
        WHERE id_solicitud = ?
    """;

    public static final String CREATE_SOLICITUD = """
        CALL sp_crear_solicitud(
            ?, ?, ?, ?, ?, ?, ?
        )
    """;

    public static final String FIND_ESTADO_BY_ID = """
        SELECT
            id_solicitud,
            estado
        FROM solicitud
        WHERE id_solicitud = ?
    """;

    public static final String CANCELAR_SOLICITUD = """
        CALL sp_cancelar_solicitud_dueno(?, ?)
    """;

    public static final String ACEPTAR_SOLICITUD = """
        CALL sp_aceptar_solicitud(?, ?)
    """;

    public static final String RECHAZAR_SOLICITUD = """
        CALL sp_rechazar_solicitud(?, ?)
    """;

    public static final String FIND_BY_WALKER_ID = """
        SELECT
            id_solicitud,
            fecha_solicitud,
            estado,
            hora_sugerida,
            punto_encuentro,
            cantidad_perros,
            observaciones,
            id_dueno,
            id_paseador
        FROM solicitud
        WHERE id_paseador = ?
        ORDER BY fecha_solicitud DESC
    """;
}
