CREATE TABLE tareas (
    id              BIGSERIAL PRIMARY KEY,
    titulo          VARCHAR(150) NOT NULL,
    descripcion     TEXT,
    fecha_vencimiento DATE,
    categoria_id INTEGER not null references categorias(id),
    usuario_id      BIGINT NOT NULL REFERENCES usuarios(id),  -- dueño de la tarea
    estatus_id      INTEGER NOT NULL REFERENCES estatus(id),
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    created_by      BIGINT NOT NULL REFERENCES usuarios(id),
    updated_by      BIGINT NOT NULL REFERENCES usuarios(id)
);

CREATE INDEX idx_tareas_usuario ON tareas(usuario_id);
CREATE INDEX idx_tareas_estatus ON tareas(estatus_id);
CREATE INDEX idx_tareas_categoria ON tareas(categoria_id);