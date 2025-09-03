INSERT INTO estado (type) VALUES ('ACTIVO');

INSERT INTO usuarios (
    name, lastname, email, password, username, birthdate, jobtitle, phone, certifications, estado_id,
    account_no_expired, account_no_locked, credential_no_expired, is_enabled, dateregister
)
VALUES (
           'Juan', 'Pérez', 'juan@example.com',
        '$2a$10$Rx9LIsCzs08pN73Ttu0R3Oh7njqeLb4YcNpNJUS2Wo3ZsN2FSOeIa', 'juanito', '1990-05-15',
           'Developer', '1234567890', 'Java, Spring',
           (SELECT id FROM estado WHERE type = 'ACTIVO'),
           true, true, true, true, CURRENT_TIMESTAMP
       )
    RETURNING idusuario;

SELECT * FROM Usuarios WHERE username = 'juanito';

INSERT INTO roles (rolesenum) VALUES ('USER');
INSERT INTO roles (rolesenum) VALUES ('ADMIN');

INSERT INTO user_roles (user_id, role_id)
VALUES (
           (SELECT idusuario FROM usuarios WHERE username = 'juanito'),
           (SELECT id FROM roles WHERE rolesenum = 'ADMIN')
       );