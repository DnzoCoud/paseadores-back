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

DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS usuario_rol;
DROP TABLE IF EXISTS paseador;
DROP TABLE IF EXISTS direccion;
DROP TABLE IF EXISTS dueno;
DROP TABLE IF EXISTS perro;
DROP TABLE IF EXISTS solicitud;
DROP TABLE IF EXISTS paseo;
DROP TABLE IF EXISTS paseo_perro;
DROP TABLE IF EXISTS calificacion;
DROP TABLE IF EXISTS solicitud_perro;
DROP TABLE IF EXISTS auditoria_paseo;
-- ============================================
-- CREACIÓN DE BASE DE DATOS PASEO DE PERROS
-- ============================================

-- ============================================
-- ROLES
-- ============================================

CREATE TABLE rol (
    id_rol BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(30) UNIQUE NOT NULL
);

-- ============================================
-- USUARIO
-- ============================================

CREATE TABLE usuario (
    id_usuario BIGSERIAL PRIMARY KEY,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena TEXT NOT NULL,
    telefono VARCHAR(20),
    primer_nombre VARCHAR(30) NOT NULL,
    segundo_nombre VARCHAR(30),
    primer_apellido VARCHAR(30) NOT NULL,
    segundo_apellido VARCHAR(30),
    foto_perfil TEXT,
    reputacion NUMERIC(3,2) DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- USUARIO_ROL
-- ============================================

CREATE TABLE usuario_rol (
    id_usuario BIGINT,
    id_rol BIGINT,

    PRIMARY KEY(id_usuario, id_rol),

    FOREIGN KEY(id_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE,

    FOREIGN KEY(id_rol)
        REFERENCES rol(id_rol)
        ON DELETE CASCADE
);

-- =========================
-- TABLA PASEADOR
-- =========================
CREATE TABLE paseador (
    id_usuario BIGINT PRIMARY KEY,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descripcion TEXT,
    disponible BOOLEAN DEFAULT TRUE,

    FOREIGN KEY(id_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
);

-- ============================================
-- DIRECCION
-- ============================================

CREATE TABLE direccion (
    id_direccion BIGSERIAL PRIMARY KEY,
    detalle VARCHAR(100) NOT NULL,
    barrio VARCHAR(50),
    ciudad VARCHAR(50),
    latitud NUMERIC(9,6),
    longitud NUMERIC(9,6)
);

-- ============================================
-- DUEÑO
-- ============================================

CREATE TABLE dueno (
    id_usuario BIGINT PRIMARY KEY,
    id_direccion BIGINT NOT NULL,
    FOREIGN KEY(id_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE,

    FOREIGN KEY(id_direccion)
        REFERENCES direccion(id_direccion)
);


-- ============================================
-- PERRO
-- ============================================

CREATE TABLE perro (
    id_perro BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    raza VARCHAR(50),
    edad INTEGER
        CHECK (edad >= 0),
    peso NUMERIC(5,2)
        CHECK (peso > 0),
    observaciones TEXT,
    foto TEXT,
    id_dueno BIGINT NOT NULL,
    FOREIGN KEY(id_dueno)
        REFERENCES dueno(id_usuario)
        ON DELETE CASCADE
);

ALTER TABLE perro
ADD COLUMN activo BOOLEAN DEFAULT TRUE;


-- ============================================
-- SOLICITUD
-- ============================================

CREATE TABLE solicitud (
    id_solicitud BIGSERIAL PRIMARY KEY,
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado estado_solicitud
        DEFAULT 'PENDIENTE',
    hora_sugerida TIME,
    punto_encuentro VARCHAR(100),
    cantidad_perros INTEGER
        CHECK (cantidad_perros > 0),
    observaciones TEXT,
    id_dueno BIGINT NOT NULL,
    id_paseador BIGINT NOT NULL,

    FOREIGN KEY(id_dueno)
        REFERENCES dueno(id_usuario),

    FOREIGN KEY(id_paseador)
        REFERENCES paseador(id_usuario)
);

-- ============================================
-- PASEO
-- ============================================

CREATE TABLE paseo (
    id_paseo BIGSERIAL PRIMARY KEY,
    estado estado_paseo
        DEFAULT 'SOLICITADO',
    fecha_inicio TIMESTAMP,
    fecha_fin TIMESTAMP,
    precio NUMERIC(10,2)
        CHECK (precio > 0),
    distancia_km NUMERIC(6,2),
    ruta TEXT,
    observaciones TEXT,
    id_solicitud BIGINT UNIQUE NOT NULL,
    id_paseador BIGINT NOT NULL,

    FOREIGN KEY(id_solicitud)
        REFERENCES solicitud(id_solicitud),

    FOREIGN KEY(id_paseador)
        REFERENCES paseador(id_usuario)
);

-- ============================================
-- PASEO_PERRO
-- ============================================

CREATE TABLE paseo_perro (
    id_paseo BIGINT,
    id_perro BIGINT,
    PRIMARY KEY(id_paseo, id_perro),

    FOREIGN KEY(id_paseo)
        REFERENCES paseo(id_paseo)
        ON DELETE CASCADE,

    FOREIGN KEY(id_perro)
        REFERENCES perro(id_perro)
        ON DELETE CASCADE
);
-- ============================================
-- CALIFICACION
-- ============================================

CREATE TABLE calificacion (
    id_calificacion BIGSERIAL PRIMARY KEY,
    puntaje puntuacion NOT NULL,
    comentario TEXT,
    fecha TIMESTAMP
        DEFAULT CURRENT_TIMESTAMP,
    id_paseo BIGINT NOT NULL,
    id_emisor BIGINT NOT NULL,
    id_receptor BIGINT NOT NULL,

    FOREIGN KEY(id_paseo)
        REFERENCES paseo(id_paseo)
        ON DELETE CASCADE,

    FOREIGN KEY(id_emisor)
        REFERENCES usuario(id_usuario),

    FOREIGN KEY(id_receptor)
        REFERENCES usuario(id_usuario)
);

CREATE TABLE solicitud_perro (

    id_solicitud BIGINT NOT NULL,

    id_perro BIGINT NOT NULL,

    PRIMARY KEY(id_solicitud, id_perro),

    FOREIGN KEY(id_solicitud)
        REFERENCES solicitud(id_solicitud)
        ON DELETE CASCADE,

    FOREIGN KEY(id_perro)
        REFERENCES perro(id_perro)
        ON DELETE CASCADE
);


CREATE TABLE auditoria_paseo (

    id_auditoria BIGSERIAL PRIMARY KEY,

    id_paseo BIGINT NOT NULL,

    accion VARCHAR(20) NOT NULL,

    estado_anterior VARCHAR(20),

    estado_nuevo VARCHAR(20),

    fecha TIMESTAMP DEFAULT NOW(),

    detalle TEXT,

    FOREIGN KEY(id_paseo)
        REFERENCES paseo(id_paseo)
);