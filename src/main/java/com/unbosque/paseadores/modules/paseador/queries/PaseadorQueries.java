package com.unbosque.paseadores.modules.paseador.queries;

public final class PaseadorQueries {
    public static final String REGISTRAR_PASEADOR = """
        SELECT *
        FROM fn_crear_paseador(
            ?, ?
        )
    """;
}
