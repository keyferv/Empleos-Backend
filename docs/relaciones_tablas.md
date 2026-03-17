Relaciones entre tablas
======================

Resumen
-------
Este documento describe las entidades JPA encontradas en el proyecto, las tablas que representan, sus campos clave y las relaciones entre ellas (cardinalidades, columnas FK, tablas puente y notas sobre cascade/fetch y comportamientos importantes).

Entidades (tablas) analizadas
-----------------------------
- Usuarios
- Roles
- Permission (permissions)
- Perfiles
- Vacantes
- OfertaDetalle (oferta_detalle)
- Solicitudes
- Categoria
- Estado
- RolesEnum (enum)
- EstadoType (enum)

Diagrama ER (texto)
-------------------
Nota: -> indica relación Many-to-One (muchos a uno), -<> indica One-to-One, <-> indica Many-to-Many (tabla intermedia mostrada).

Usuarios (user_id)                          Roles (id)
    |                                           |
    | (many-to-many via user_roles)             | (many-to-many via role_permissions)
    |----------------------------------<->---------<->--- Permission (id)

Vacantes (id)  1 -<> OfertaDetalle (id)
    |  \_ (1) <-> categorias via vacante_categoria <-> Categoria (id)
    |
    |-> Solicitudes (many) -> Estado (estado_id)

Usuarios -> Estado (estado_id)

Entidad: Usuarios
-----------------
- Tabla: usuarios (entidad `Usuarios`)
- PK: `idUsuario` (Integer)
- Campos destacables: name, lastname, email, username, password, dateRegister, birthDate, jobTitle, phone, certifications, flags (isEnabled, accountNoExpired, accountNoLocked, credentialNoExpired)
- Relaciones:
  - Many-to-Many con `Roles` mediante la tabla puente `user_roles` (joinColumns: user_id, inverseJoinColumns: role_id).
  - Many-to-One con `Estado` (columna `estado_id`, no nullable).
- Notas:
  - `@PrePersist` establece `dateRegister` y asigna un `Estado` por defecto (EstadoType.ACTIVO) si no se pasó uno.
  - El mapeo OneToMany hacia `Solicitudes` está comentado en el código; actualmente no existe FK desde `Solicitudes` hacia `Usuarios` en la implementación activa.

Entidad: Roles
--------------
- Tabla: `roles`
- PK: id (Integer)
- Campos: rolesEnum (enum de tipo RolesEnum)
- Relaciones:
  - Many-to-Many con `Permission` mediante tabla puente `role_permissions` (joinColumns: role_id, inverseJoinColumns: permission_id).
  - Many-to-Many con `Usuarios` (tabla puente `user_roles`) — mapeado en `Usuarios`.
- Notas:
  - `rolesEnum` se almacena como STRING.

Entidad: Permission
-------------------
- Tabla: `permissions`
- PK: id (Long)
- Campos: name (unique, not null, no updatable)
- Relaciones:
  - Many-to-Many con `Roles` (tabla puente `role_permissions`).

Entidad: Perfiles
------------------
- Tabla: `Perfiles` (entidad `Perfiles`)
- PK: id (Integer)
- Campos: perfil (String)
- Relaciones: ninguna definida en el código (tabla independiente).

Entidad: Vacantes
-----------------
- Tabla: `Vacantes` (entidad `Vacantes`)
- PK: id (Integer)
- Campos: offerName (offer_name), description, date, salary, featured, image, etc.
- Relaciones:
  - Many-to-One con `Estado` (columna `estado_id`, no nullable).
  - One-to-Many con `Solicitudes`: mapeado mediante `@OneToMany(mappedBy = "vacancy")` en `Vacantes` y `@ManyToOne` en `Solicitudes`. (Vacante 1 - N Solicitudes)
  - One-to-One con `OfertaDetalle` (tabla `oferta_detalle`) via columna `id_detalle` en `vacantes` que referencia `oferta_detalle.id`. Configuración: fetch LAZY, optional = false, cascade ALL, orphanRemoval = true.
  - Many-to-Many con `Categoria` mediante tabla puente `vacante_categoria` (vacante_id, categoria_id).
- Notas:
  - La relación OneToOne tiene cascade = ALL y orphanRemoval = true, por lo que el ciclo de vida del `OfertaDetalle` está fuertemente ligado a `Vacantes`.
  - `requests` es un Set de `Solicitudes` con cascade ALL y orphanRemoval = true.

Entidad: OfertaDetalle
----------------------
- Tabla: `oferta_detalle`
- PK: id (Integer)
- Campos: requirements, responsibilities, benefits, location, startHour, endHour, processSelection, salary
- Relaciones:
  - One-to-One bidireccional/propiedad en `Vacantes` (el owning side es `Vacantes` por la columna `id_detalle`).

Entidad: Solicitudes
---------------------
- Tabla: `Solicitudes` (entidad `Solicitudes`)
- PK: id (Integer)
- Campos: requestDate, file, comment
- Relaciones:
  - Many-to-One con `Vacantes` (`@JoinColumn(name = "vacante_id")`, propiedad `vacancy` en la entidad).
  - Many-to-One con `Estado` (`@JoinColumn(name = "estado_id", nullable = false)`).
  - (Comentado) Many-to-One con `Usuarios` está comentado en el código; por tanto no existe actualmente relación activa entre `Solicitudes` y `Usuarios`.
- Notas:
  - Fetch EAGER en las relaciones actuales (vacancy y estado están con fetch = EAGER); cuidado por N+1 si se consultan muchas solicitudes.

Entidad: Categoria
------------------
- Tabla: `Categoria` (entidad `Categoria`)
- PK: id (Integer)
- Campos: categoryName (category_name), description
- Relaciones:
  - Many-to-Many con `Vacantes` (mapeado por `Vacantes#categories`). La tabla puente es `vacante_categoria`.

Entidad: Estado
----------------
- Tabla: `Estado` (entidad `Estado`)
- PK: id (Integer)
- Campos: type (enum EstadoType) — columna unique y not null.
- Relaciones:
  - Referenciado por `Usuarios`, `Vacantes` y `Solicitudes` mediante `estado_id`.
- Notas:
  - Construcción rápida `new Estado(EstadoType.ACTIVO)` está usada en `Usuarios.prePersist()` para asignar estado por defecto.

Enums
-----
- RolesEnum: ADMIN, USER, SuperVisor
- EstadoType: ACTIVO, INACTIVO, PENDIENTE, SUSPENDIDO

Tablas puente
-------------
- `user_roles` (user_id, role_id) — une `Usuarios` y `Roles`.
- `role_permissions` (role_id, permission_id) — une `Roles` y `Permission`.
- `vacante_categoria` (vacante_id, categoria_id) — une `Vacantes` y `Categoria`.

Claves foráneas (resumen)
-------------------------
- `usuarios.estado_id` -> `estado.id`
- `vacantes.estado_id` -> `estado.id`
- `vacantes.id_detalle` -> `oferta_detalle.id`
- `solicitudes.vacante_id` -> `vacantes.id`
- `solicitudes.estado_id` -> `estado.id`
- `user_roles.user_id` -> `usuarios.idUsuario` (columna definida en el @JoinTable de `Usuarios`)
- `user_roles.role_id` -> `roles.id`
- `role_permissions.role_id` -> `roles.id`
- `role_permissions.permission_id` -> `permissions.id`
- `vacante_categoria.vacante_id` -> `vacantes.id`
- `vacante_categoria.categoria_id` -> `categoria.id`

Comportamientos importantes y recomendaciones
--------------------------------------------
- Cascada y orphanRemoval:
  - `Vacantes.detail` (OneToOne) usa cascade = ALL y orphanRemoval = true. Si se elimina una vacante, su `OfertaDetalle` también se eliminará.
  - `Vacantes.requests` tiene cascade ALL y orphanRemoval true: eliminar una vacante elimina sus solicitudes.
- Fetch:
  - `Solicitudes.vacancy` y `Solicitudes.estado` están con FetchType.EAGER — revisar si esto causa problemas de rendimiento (N+1) si hay consultas masivas.
  - `Vacantes.detail` es LAZY — acceder al detalle requerirá inicialización explícita si se serializa la vacante.
- Relaciones comentadas:
  - En `Solicitudes` y `Usuarios` hay mapeos comentados que, si se activan, agregarían FK y navegación entre usuario y solicitud. Revisar por qué fueron comentados (posible conflicto de diseño o ciclo de dependencia).
- Unicidad en Estado:
  - `Estado.type` es unique, por tanto sólo puede existir una fila por cada valor de `EstadoType`.

Casos límite y validaciones de integridad
----------------------------------------
- `Estado` es requerido en `Solicitudes` y en `Vacantes` según las anotaciones (nullable = false).
- `OfertaDetalle` es `optional = false` en la relación OneToOne con `Vacantes` — significa que cada vacante debe tener un detalle asociado.
- `Permission.name` es único y no actualizable.

Mapa rápido de relaciones (tabla -> tablas relacionadas)
-------------------------------------------------------
- Usuarios -> Roles (M:N via user_roles), Estado (N:1)
- Roles -> Permission (M:N via role_permissions), Usuarios (M:N via user_roles)
- Permission -> Roles (M:N)
- Vacantes -> OfertaDetalle (1:1), Categoria (M:N), Solicitudes (1:N), Estado (N:1)
- Solicitudes -> Vacantes (N:1), Estado (N:1)
- Categoria -> Vacantes (M:N)
- Estado -> Usuarios, Vacantes, Solicitudes (referenciado por FK)

Siguientes pasos recomendados
----------------------------
1. Documentar en la base de datos las tablas puente (`user_roles`, `role_permissions`, `vacante_categoria`) si se usa un script de migración (Flyway/Liquibase).
2. Evaluar cambiar FetchType.EAGER a LAZY en `Solicitudes` si se observan problemas de rendimiento.
3. Revisar si debe existir relación entre `Solicitudes` y `Usuarios` (actualmente comentada).
4. Añadir constraints/indices en columnas usadas para búsqueda (email, username, estado.type, permission.name).

Cobertura de requisitos
-----------------------
- Identificar todas las entidades y tablas: Done
- Describir relaciones (cardinalidad, FK, tablas puente): Done
- Notas sobre cascada y fetch: Done
- Recomendaciones operacionales: Done

Si quieres, puedo:
- Generar un diagrama ER visual (PlantUML o Graphviz) en `docs/`.
- Crear scripts SQL de creación de tablas basados en estas entidades.
- Activar y documentar las relaciones comentadas (por ejemplo entre `Solicitudes` y `Usuarios`).


