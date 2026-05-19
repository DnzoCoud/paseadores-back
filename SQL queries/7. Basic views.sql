CREATE OR REPLACE VIEW vw_historial_paseos AS
SELECT
    p.id_paseo,
    p.estado,
    p.fecha_inicio,
    p.fecha_fin,
    p.precio,
    s.estado AS estado_solicitud,
    d.id_usuario AS id_dueno,
    u_dueno.primer_nombre || ' ' ||
    u_dueno.primer_apellido
        AS dueno,
    pa.id_usuario AS id_paseador,
    u_paseador.primer_nombre || ' ' ||
    u_paseador.primer_apellido
        AS paseador
FROM paseo p
INNER JOIN solicitud s
    ON s.id_solicitud = p.id_solicitud
INNER JOIN dueno d
    ON d.id_usuario = s.id_dueno
INNER JOIN usuario u_dueno
    ON u_dueno.id_usuario = d.id_usuario
INNER JOIN paseador pa
    ON pa.id_usuario = p.id_paseador
INNER JOIN usuario u_paseador
    ON u_paseador.id_usuario = pa.id_usuario;
    
CREATE OR REPLACE VIEW vw_ranking_paseadores AS
SELECT
    p.id_usuario,
    u.primer_nombre,
    u.primer_apellido,
    ROUND(AVG(c.puntaje), 2)
        AS reputacion,
    COUNT(c.id_calificacion)
        AS total_calificaciones

FROM paseador p
INNER JOIN usuario u
    ON u.id_usuario = p.id_usuario
LEFT JOIN calificacion c
    ON c.id_receptor = p.id_usuario
GROUP BY
    p.id_usuario,
    u.primer_nombre,
    u.primer_apellido;
    
CREATE MATERIALIZED VIEW mv_ranking_paseadores AS
WITH reputaciones AS (
         SELECT p.id_usuario,
            u.primer_nombre,
            COALESCE(avg((c.puntaje)::numeric), (0)::numeric) AS reputacion
           FROM ((paseador p
             JOIN usuario u ON ((u.id_usuario = p.id_usuario)))
             LEFT JOIN calificacion c ON ((c.id_receptor = p.id_usuario)))
          GROUP BY p.id_usuario, u.primer_nombre
        )
 SELECT id_usuario,
    primer_nombre,
    round(reputacion, 2) AS reputacion,
    dense_rank() OVER (ORDER BY reputaciones.reputacion DESC) AS ranking
   FROM reputaciones;