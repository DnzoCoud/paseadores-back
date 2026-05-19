CREATE OR REPLACE PROCEDURE sp_crear_solicitud(

    IN p_hora_sugerida TIME,
    IN p_punto_encuentro VARCHAR,
    IN p_observaciones TEXT,

    IN p_id_dueno BIGINT,
    IN p_id_paseador BIGINT,

    IN p_perros BIGINT[],

    OUT p_id_solicitud BIGINT

)
LANGUAGE plpgsql
AS
$$
DECLARE

    v_id_perro BIGINT;

BEGIN

    IF array_length(p_perros, 1) IS NULL THEN

        RAISE EXCEPTION
            'Debe seleccionar al menos un perro';

    END IF;

    IF NOT EXISTS(
        SELECT 1
        FROM dueno
        WHERE id_usuario = p_id_dueno
    ) THEN

        RAISE EXCEPTION
            'El usuario no es dueño';

    END IF;

    IF NOT EXISTS(
        SELECT 1
        FROM paseador
        WHERE id_usuario = p_id_paseador
    ) THEN

        RAISE EXCEPTION
            'El usuario no es paseador';

    END IF;

    INSERT INTO solicitud(

        fecha_solicitud,
        estado,
        hora_sugerida,
        punto_encuentro,
        cantidad_perros,
        observaciones,
        id_dueno,
        id_paseador

    )
    VALUES (

        NOW(),
        'PENDIENTE',
        p_hora_sugerida,
        p_punto_encuentro,
        array_length(p_perros, 1),
        p_observaciones,
        p_id_dueno,
        p_id_paseador

    )
    RETURNING id_solicitud
    INTO p_id_solicitud;

    FOREACH v_id_perro IN ARRAY p_perros
    LOOP

        IF NOT EXISTS(
            SELECT 1
            FROM perro
            WHERE id_perro = v_id_perro
            AND id_dueno = p_id_dueno
            AND activo = true
        ) THEN

            RAISE EXCEPTION
                'Perro inválido';
        END IF;

        INSERT INTO solicitud_perro(

            id_solicitud,
            id_perro

        )
        VALUES (

            p_id_solicitud,
            v_id_perro

        );

    END LOOP;

END;
$$;

CREATE OR REPLACE PROCEDURE sp_aceptar_solicitud(

    IN p_id_solicitud BIGINT,
    IN p_id_paseador BIGINT,
    INOUT p_id_paseo BIGINT

)
LANGUAGE plpgsql
AS
$$
DECLARE

    v_estado estado_solicitud;

BEGIN

    SELECT estado
    INTO v_estado
    FROM solicitud
    WHERE id_solicitud = p_id_solicitud
    AND id_paseador = p_id_paseador;

    IF NOT FOUND THEN

        RAISE EXCEPTION
            'Solicitud no encontrada para el paseador';

    END IF;

    IF v_estado <> 'PENDIENTE' THEN

        RAISE EXCEPTION
            'Solo se pueden aceptar solicitudes pendientes';

    END IF;

    UPDATE solicitud
    SET estado = 'ACEPTADA'
    WHERE id_solicitud = p_id_solicitud
    AND id_paseador = p_id_paseador;
    
    UPDATE paseador
    SET disponible = false
    WHERE id_usuario = p_id_paseador;
    
    INSERT INTO paseo(

        estado,
        fecha_inicio,
        precio,
        distancia_km,
        ruta,
        observaciones,
        id_solicitud,
        id_paseador

    )
    VALUES (

        'INICIADO',
        NOW(),
        1,
        0,
        '',
        'Paseo iniciado automáticamente',
        p_id_solicitud,
        p_id_paseador

    )
    RETURNING id_paseo
    INTO p_id_paseo;

    INSERT INTO paseo_perro(

        id_paseo,
        id_perro

    )
    SELECT
        p_id_paseo,
        sp.id_perro
    FROM solicitud_perro sp
    WHERE sp.id_solicitud = p_id_solicitud;

END;
$$;

CREATE OR REPLACE PROCEDURE sp_rechazar_solicitud(

    IN p_id_solicitud BIGINT,
    IN p_id_paseador BIGINT

)
LANGUAGE plpgsql
AS
$$
DECLARE

    v_estado estado_solicitud;

BEGIN

    SELECT estado
    INTO v_estado
    FROM solicitud
    WHERE id_solicitud = p_id_solicitud
    AND id_paseador = p_id_paseador;

    IF NOT FOUND THEN

        RAISE EXCEPTION
            'Solicitud no encontrada para el paseador';

    END IF;

    IF v_estado <> 'PENDIENTE' THEN

        RAISE EXCEPTION
            'Solo se pueden rechazar solicitudes pendientes';

    END IF;

    UPDATE solicitud
    SET estado = 'CANCELADA'
    WHERE id_solicitud = p_id_solicitud
    AND id_paseador = p_id_paseador;

END;
$$;
  
CREATE OR REPLACE PROCEDURE sp_cancelar_solicitud_dueno(

    IN p_id_solicitud BIGINT,
    IN p_id_dueno BIGINT

)
LANGUAGE plpgsql
AS
$$
DECLARE

    v_estado estado_solicitud;

BEGIN

    SELECT estado
    INTO v_estado
    FROM solicitud
    WHERE id_solicitud = p_id_solicitud
    AND id_dueno = p_id_dueno;

    IF NOT FOUND THEN

        RAISE EXCEPTION
            'Solicitud no encontrada para el dueño';

    END IF;

    IF v_estado <> 'PENDIENTE' THEN

        RAISE EXCEPTION
            'Solo se pueden cancelar solicitudes pendientes';

    END IF;

    UPDATE solicitud
    SET estado = 'CANCELADA'
    WHERE id_solicitud = p_id_solicitud
    AND id_dueno = p_id_dueno;

END;
$$;


CREATE OR REPLACE PROCEDURE sp_finalizar_paseo(

    IN p_id_paseo BIGINT,
    IN p_id_paseador BIGINT

)
LANGUAGE plpgsql
AS
$$
DECLARE

    v_estado estado_paseo;

BEGIN

    SELECT estado
    INTO v_estado
    FROM paseo
    WHERE id_paseo = p_id_paseo
    AND id_paseador = p_id_paseador;

    IF NOT FOUND THEN

        RAISE EXCEPTION
            'Paseo no encontrado para el paseador';

    END IF;

    IF v_estado <> 'EN_CURSO' THEN

        RAISE EXCEPTION
            'Solo se pueden finalizar paseos iniciados';

    END IF;

    UPDATE paseo
    SET
        estado = 'FINALIZADO',
        fecha_fin = NOW()
    WHERE id_paseo = p_id_paseo;

    UPDATE paseador
    SET disponible = true
    WHERE id_usuario = p_id_paseador;

END;
$$;

CREATE OR REPLACE PROCEDURE sp_calificar_paseo(
    IN p_id_paseo BIGINT,
    IN p_id_dueno BIGINT,
    IN p_puntaje puntuacion,
    IN p_comentario TEXT

)
LANGUAGE plpgsql
AS
$$
DECLARE

    v_id_paseador BIGINT;

    v_estado estado_paseo;

BEGIN

    SELECT
        p.id_paseador,
        p.estado
    INTO
        v_id_paseador,
        v_estado
    FROM paseo p

    INNER JOIN solicitud s
        ON s.id_solicitud = p.id_solicitud

    WHERE p.id_paseo = p_id_paseo
    AND s.id_dueno = p_id_dueno;

    IF NOT FOUND THEN

        RAISE EXCEPTION
            'Paseo no encontrado para el dueño';

    END IF;

    IF v_estado <> 'FINALIZADO' THEN

        RAISE EXCEPTION
            'Solo se pueden calificar paseos finalizados';

    END IF;

    IF EXISTS(
        SELECT 1
        FROM calificacion
        WHERE id_paseo = p_id_paseo
        AND id_emisor = p_id_dueno
    ) THEN

        RAISE EXCEPTION
            'El paseo ya fue calificado';

    END IF;

    INSERT INTO calificacion(

        puntaje,
        comentario,
        fecha,
        id_paseo,
        id_emisor,
        id_receptor

    )
    VALUES (

        p_puntaje,
        p_comentario,
        NOW(),
        p_id_paseo,
        p_id_dueno,
        v_id_paseador

    );

END;
$$;
