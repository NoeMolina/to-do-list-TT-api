ALTER TABLE usuarios ADD COLUMN rol_id INTEGER REFERENCES roles(id);

UPDATE usuarios SET rol_id = (SELECT id FROM roles WHERE codigo = 'USER')
WHERE rol_id IS NULL;

ALTER TABLE usuarios ALTER COLUMN rol_id SET NOT NULL;

CREATE INDEX idx_usuarios_rol ON usuarios(rol_id);