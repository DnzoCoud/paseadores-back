package com.unbosque.paseadores.modules.analitycs.ranking.queries;

public final class RankingQueries {

    private RankingQueries() {}

    public static final String FIND_ALL = """
        SELECT
            id_usuario,
            primer_nombre,
            reputacion,
            ranking
        FROM mv_ranking_paseadores
        ORDER BY ranking ASC
    """;

    public static final String FIND_TOP_10 = """
        SELECT
            id_usuario,
            primer_nombre,
            reputacion,
            ranking
        FROM mv_ranking_paseadores
        ORDER BY ranking ASC
        LIMIT 10
    """;

    public static final String FIND_BY_ID = """
        SELECT
            id_usuario,
            primer_nombre,
            reputacion,
            ranking
        FROM mv_ranking_paseadores
        WHERE id_usuario = ?
    """;

    public static final String REFRESH_RANKING = """
        REFRESH MATERIALIZED VIEW mv_ranking_paseadores
    """;
}
