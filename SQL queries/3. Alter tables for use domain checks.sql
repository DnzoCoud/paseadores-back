-- ============================================
-- ALTER TABLE PARA USAR DOMAINS
-- ============================================

-- =========================
-- CAMBIAR ESTADO EN PASEO
-- =========================
ALTER TABLE paseo
ALTER COLUMN estado
TYPE estado_paseo
USING estado::estado_paseo;

-- =========================
-- CAMBIAR ESTADO EN SOLICITUD
-- =========================
ALTER TABLE solicitud
ALTER COLUMN estado
TYPE estado_solicitud
USING estado::estado_solicitud;