package com.unbosque.paseadores.modules.users.queries;

public final class UserQueries {
    private UserQueries() {}

    public static final String FIND_BY_ID = "SELECT * FROM usuario WHERE id_usuario = ?";
    public static final String FIND_BY_EMAIL = "SELECT * FROM usuario WHERE correo = ?";
    public static final String FIND_ROLES_BY_USER_ID = """
        SELECT
            r.id_rol,
            r.nombre
        FROM rol r
        INNER JOIN usuario_rol ur
            ON ur.id_rol = r.id_rol
        WHERE ur.id_usuario = ?
    """;
    public static final String SAVE_USER = """
        SELECT *
        FROM fn_registrar_usuario(
            ?, ?, ?, ?, ?, ?, ?, ?, ?
        )
    """;

    public static final String EXISTS_BY_EMAIL = """
        SELECT EXISTS(
            SELECT 1
            FROM usuario
            WHERE correo = ?
        )
    """;
}
