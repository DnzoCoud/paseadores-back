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