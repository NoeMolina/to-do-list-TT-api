CREATE TABLE estatus (
    id              SERIAL PRIMARY KEY,
    codigo          VARCHAR(30) NOT NULL UNIQUE,   -- PENDIENTE, EN_PROGRESO, COMPLETADO, CANCELADO
    nombre          VARCHAR(50) NOT NULL
);