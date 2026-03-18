-- Actualizar esquema para soportar expiración
ALTER TABLE vacantes ADD COLUMN IF NOT EXISTS expiration_date TIMESTAMP;

-- Establecer una fecha de expiración por defecto (30 días después de la creación) para las existentes
UPDATE vacantes SET expiration_date = date + interval '30 days' WHERE expiration_date IS NULL;

-- Asegurar que existan los nuevos estados
INSERT INTO estado (type) VALUES ('CERRADO') ON CONFLICT (type) DO NOTHING;
INSERT INTO estado (type) VALUES ('EXPIRADO') ON CONFLICT (type) DO NOTHING;
