CREATE DOMAIN estado_paseo AS VARCHAR(20)
CHECK (
    VALUE IN (
        'SOLICITADO',
        'ACEPTADO',
        'EN_CURSO',
        'FINALIZADO',
        'CANCELADO'
    )
);

CREATE DOMAIN estado_solicitud AS VARCHAR(20)
CHECK (
    VALUE IN (
        'PENDIENTE',
        'ACEPTADA',
        'RECHAZADA',
        'CANCELADA'
    )
);

CREATE DOMAIN puntuacion AS NUMERIC(2,1)
CHECK (
    VALUE >= 0
    AND VALUE <= 5
);


CREATE DOMAIN dia_semana AS VARCHAR(15)
CHECK (
    VALUE IN (
        'LUNES',
        'MARTES',
        'MIERCOLES',
        'JUEVES',
        'VIERNES',
        'SABADO',
        'DOMINGO'
    )
);

CREATE SEQUENCE seq_paseo START 1;
CREATE SEQUENCE seq_solicitud START 1;
CREATE SEQUENCE seq_mensaje START 1;
CREATE SEQUENCE seq_calificacion START 1;
CREATE SEQUENCE seq_perro START 1;



CREATE INDEX idx_usuario_correo
ON usuario(correo);

CREATE INDEX idx_paseo_estado
ON paseo(estado);

CREATE INDEX idx_solicitud_estado
ON solicitud(estado);

CREATE INDEX idx_mensaje_paseo
ON mensaje(id_paseo);

CREATE INDEX idx_calificacion_receptor
ON calificacion(id_receptor);

CREATE INDEX idx_disponibilidad_paseador
ON disponibilidad(id_paseador);