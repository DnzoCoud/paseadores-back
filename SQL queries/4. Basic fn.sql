CREATE OR REPLACE FUNCTION fn_registrar_usuario(

    p_correo VARCHAR,
    p_contrasena VARCHAR,
    p_telefono VARCHAR,
    p_primer_nombre VARCHAR,
    p_segundo_nombre VARCHAR,
    p_primer_apellido VARCHAR,
    p_segundo_apellido VARCHAR,
    p_foto_perfil VARCHAR,
    p_roles BIGINT[]

)
RETURNS SETOF usuario
LANGUAGE plpgsql
AS
$$
DECLARE

    v_id_usuario BIGINT;

    v_rol_id BIGINT;

BEGIN

    IF EXISTS(
        SELECT 1
        FROM usuario
        WHERE usuario.correo = p_correo
    ) THEN

        RAISE EXCEPTION
            'El correo ya está registrado';

    END IF;

    INSERT INTO usuario(

        correo,
        contrasena,
        telefono,
        primer_nombre,
        segundo_nombre,
        primer_apellido,
        segundo_apellido,
        foto_perfil,
        reputacion,
        activo,
        created_at,
        updated_at

    )
    VALUES (

        p_correo,
        p_contrasena,
        p_telefono,
        p_primer_nombre,
        p_segundo_nombre,
        p_primer_apellido,
        p_segundo_apellido,
        p_foto_perfil,
        0,
        true,
        NOW(),
        NOW()

    )
    RETURNING usuario.id_usuario
    INTO v_id_usuario;

    IF p_roles IS NOT NULL THEN

        FOREACH v_rol_id IN ARRAY p_roles
        LOOP

            INSERT INTO usuario_rol(

                id_usuario,
                id_rol

            )
            VALUES (

                v_id_usuario,
                v_rol_id

            );

        END LOOP;

    END IF;

    RETURN QUERY

    SELECT *
    FROM usuario
    WHERE usuario.id_usuario = v_id_usuario;

END;
$$;

CREATE OR REPLACE FUNCTION fn_crear_paseador(
    p_id_usuario BIGINT,
    p_descripcion TEXT
)
RETURNS SETOF paseador
LANGUAGE plpgsql
AS
$$
BEGIN

    IF EXISTS(
        SELECT 1
        FROM paseador
        WHERE id_usuario = p_id_usuario
    ) THEN

        RAISE EXCEPTION
            'El usuario ya es paseador';

    END IF;

    INSERT INTO paseador(

        id_usuario,
        fecha_registro,
        descripcion,
        disponible

    )
    VALUES (

        p_id_usuario,
        NOW(),
        p_descripcion,
        true

    );

    RETURN QUERY

    SELECT *
    FROM paseador
    WHERE paseador.id_usuario = p_id_usuario;

END;
$$;


CREATE OR REPLACE FUNCTION fn_auditoria_paseo()
RETURNS TRIGGER
LANGUAGE plpgsql
AS
$$
BEGIN

    IF TG_OP = 'INSERT' THEN

        INSERT INTO auditoria_paseo(

            id_paseo,
            accion,
            estado_nuevo,
            detalle

        )
        VALUES (

            NEW.id_paseo,
            'CREACION',
            NEW.estado,
            'Se creó el paseo'
        );

    ELSIF TG_OP = 'UPDATE' THEN

        INSERT INTO auditoria_paseo(

            id_paseo,
            accion,
            estado_anterior,
            estado_nuevo,
            detalle

        )
        VALUES (

            NEW.id_paseo,
            'ACTUALIZACION',
            OLD.estado,
            NEW.estado,
            'Se actualizó el estado del paseo'
        );

    ELSIF TG_OP = 'DELETE' THEN

        INSERT INTO auditoria_paseo(

            id_paseo,
            accion,
            estado_anterior,
            detalle

        )
        VALUES (

            OLD.id_paseo,
            'ELIMINACION',
            OLD.estado,
            'Se eliminó el paseo'
        );

    END IF;

    RETURN NEW;

END;
$$;