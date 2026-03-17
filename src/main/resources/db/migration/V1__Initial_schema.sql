-- ============================================
-- Flyway Migration Script: V1__Initial_schema.sql
-- Created: 2026-03-16
-- Description: Initial database schema for Empleos-Backend
-- ============================================

-- ============================================
-- Table: estado
-- ============================================
CREATE TABLE IF NOT EXISTS estado (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL UNIQUE
);

-- Insert default estado types
INSERT INTO estado (type) VALUES ('ACTIVO'), ('INACTIVO'), ('PENDIENTE'), ('SUSPENDIDO')
ON CONFLICT (type) DO NOTHING;

-- ============================================
-- Table: categoria
-- ============================================
CREATE TABLE IF NOT EXISTS categoria (
    id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    description TEXT
);

-- ============================================
-- Table: oferta_detalle
-- ============================================
CREATE TABLE IF NOT EXISTS oferta_detalle (
    id SERIAL PRIMARY KEY,
    requirements TEXT,
    responsibilities TEXT,
    benefits TEXT,
    location VARCHAR(200),
    start_hour TIME,
    end_hour TIME,
    process_selection TEXT,
    salary DECIMAL(10, 2)
);

-- ============================================
-- Table: vacantes
-- ============================================
CREATE TABLE IF NOT EXISTS vacantes (
    id SERIAL PRIMARY KEY,
    offer_name VARCHAR(200) NOT NULL,
    description TEXT,
    date DATE,
    salary DECIMAL(10, 2),
    image VARCHAR(500),
    featured BOOLEAN DEFAULT FALSE,
    estado_id INTEGER NOT NULL,
    id_detalle INTEGER NOT NULL,
    CONSTRAINT fk_vacantes_estado FOREIGN KEY (estado_id) REFERENCES estado(id),
    CONSTRAINT fk_vacantes_detalle FOREIGN KEY (id_detalle) REFERENCES oferta_detalle(id),
    CONSTRAINT uq_vacantes_detalle UNIQUE (id_detalle)
);

-- ============================================
-- Table: vacante_categoria (Many-to-Many join table)
-- ============================================
CREATE TABLE IF NOT EXISTS vacante_categoria (
    vacante_id INTEGER NOT NULL,
    categoria_id INTEGER NOT NULL,
    PRIMARY KEY (vacante_id, categoria_id),
    CONSTRAINT fk_vc_vacante FOREIGN KEY (vacante_id) REFERENCES vacantes(id) ON DELETE CASCADE,
    CONSTRAINT fk_vc_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE CASCADE
);

-- ============================================
-- Table: permissions
-- ============================================
CREATE TABLE IF NOT EXISTS permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- ============================================
-- Table: roles
-- ============================================
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    roles_enum VARCHAR(20) NOT NULL UNIQUE
);

-- Insert default roles
INSERT INTO roles (roles_enum) VALUES ('ADMIN'), ('USER'), ('SuperVisor')
ON CONFLICT (roles_enum) DO NOTHING;

-- ============================================
-- Table: role_permissions (Many-to-Many join table)
-- ============================================
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id INTEGER NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_rp_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    CONSTRAINT fk_rp_permission FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- ============================================
-- Table: usuarios
-- ============================================
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    date_register TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    birth_date DATE,
    job_title VARCHAR(100),
    phone VARCHAR(20),
    certifications TEXT,
    is_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_no_expired BOOLEAN NOT NULL DEFAULT TRUE,
    account_no_locked BOOLEAN NOT NULL DEFAULT TRUE,
    credential_no_expired BOOLEAN NOT NULL DEFAULT TRUE,
    estado_id INTEGER NOT NULL,
    CONSTRAINT fk_usuarios_estado FOREIGN KEY (estado_id) REFERENCES estado(id)
);

-- ============================================
-- Table: user_roles (Many-to-Many join table)
-- ============================================
CREATE TABLE IF NOT EXISTS user_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- ============================================
-- Table: solicitudes
-- ============================================
CREATE TABLE IF NOT EXISTS solicitudes (
    id SERIAL PRIMARY KEY,
    request_date DATE NOT NULL DEFAULT CURRENT_DATE,
    file VARCHAR(500),
    comment TEXT,
    vacante_id INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    estado_id INTEGER NOT NULL,
    CONSTRAINT fk_solicitudes_vacante FOREIGN KEY (vacante_id) REFERENCES vacantes(id) ON DELETE CASCADE,
    CONSTRAINT fk_solicitudes_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_solicitudes_estado FOREIGN KEY (estado_id) REFERENCES estado(id)
);

-- ============================================
-- Table: perfiles
-- ============================================
CREATE TABLE IF NOT EXISTS perfiles (
    id SERIAL PRIMARY KEY,
    perfil VARCHAR(50) NOT NULL
);

-- ============================================
-- Indexes for better performance
-- ============================================
CREATE INDEX idx_vacantes_estado ON vacantes(estado_id);
CREATE INDEX idx_vacantes_detalle ON vacantes(id_detalle);
CREATE INDEX idx_vacante_categoria_vacante ON vacante_categoria(vacante_id);
CREATE INDEX idx_vacante_categoria_categoria ON vacante_categoria(categoria_id);
CREATE INDEX idx_usuarios_estado ON usuarios(estado_id);
CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_username ON usuarios(username);
CREATE INDEX idx_solicitudes_vacante ON solicitudes(vacante_id);
CREATE INDEX idx_solicitudes_usuario ON solicitudes(usuario_id);
CREATE INDEX idx_solicitudes_estado ON solicitudes(estado_id);
CREATE INDEX idx_user_roles_user ON user_roles(user_id);
CREATE INDEX idx_user_roles_role ON user_roles(role_id);
CREATE INDEX idx_role_permissions_role ON role_permissions(role_id);
CREATE INDEX idx_role_permissions_permission ON role_permissions(permission_id);

-- ============================================
-- Comments on tables
-- ============================================
COMMENT ON TABLE estado IS 'Store system status types (ACTIVO, INACTIVO, etc.)';
COMMENT ON TABLE categoria IS 'Job vacancy categories';
COMMENT ON TABLE oferta_detalle IS 'Detailed information about job offers';
COMMENT ON TABLE vacantes IS 'Job vacancies/offers';
COMMENT ON TABLE vacante_categoria IS 'Many-to-many relationship between vacancies and categories';
COMMENT ON TABLE permissions IS 'System permissions for fine-grained access control';
COMMENT ON TABLE roles IS 'User roles (ADMIN, USER, SuperVisor)';
COMMENT ON TABLE role_permissions IS 'Many-to-many relationship between roles and permissions';
COMMENT ON TABLE usuarios IS 'System users';
COMMENT ON TABLE user_roles IS 'Many-to-many relationship between users and roles';
COMMENT ON TABLE solicitudes IS 'Job applications submitted by users';
COMMENT ON TABLE perfiles IS 'User profiles (additional information)';