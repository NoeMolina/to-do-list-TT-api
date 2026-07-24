CREATE TABLE subtareas (
    id              BIGSERIAL PRIMARY KEY,
    tarea_id        BIGINT NOT NULL REFERENCES tareas(id) ON DELETE CASCADE,
    titulo          VARCHAR(150) NOT NULL,
    descripcion     TEXT,
    estatus_id      INTEGER NOT NULL REFERENCES estatus(id),
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by      BIGINT NOT NULL REFERENCES usuarios(id),
    updated_by      BIGINT NOT NULL REFERENCES usuarios(id)
);

CREATE INDEX idx_subtareas_tarea ON subtareas(tarea_id);
CREATE INDEX idx_subtareas_estatus ON subtareas(estatus_id);