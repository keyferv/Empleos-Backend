Empleos-Backend
================

Resumen
-------
Proyecto backend en Java (Spring Boot) para la gestión de ofertas de trabajo (vacantes), solicitudes y usuarios. Este README se centra en explicar cómo funcionan las relaciones entre las entidades del dominio y cómo se reflejan en la base de datos.

Tecnologías
----------
- Java 17+
- Spring Boot (JPA/Hibernate)
- PostgreSQL
- Lombok
- Maven

Configuración de la base de datos
---------------------------------
La configuración principal está en `src/main/resources/application.properties`.
Valores por defecto en este repositorio:
- URL: `jdbc:postgresql://localhost:5432/empleos`
- Usuario: `postgres`
- Password: `1234`

Asegúrate de crear la base de datos `empleos` y configurar las credenciales si difieren.

Resumen de entidades y relaciones
---------------------------------
A continuación se describen las entidades más importantes y cómo se relacionan entre sí en el código del proyecto.

1) Vacantes
- Tabla: `vacantes` (entidad `Vacantes`)
- Campos relevantes: `id`, `offerName`, `description`, `date`, `salary`, `image`, `featured`.
- Relaciones:
  - ManyToOne -> `Estado` (columna `estado_id`): cada vacante tiene un estado (ACTIVO, INACTIVO, ...).
  - OneToMany -> `Solicitudes` (mappedBy = "vacancy"): una vacante puede tener muchas solicitudes. En la entidad `Vacantes` se define `private Set<Solicitudes> requests` con cascade ALL y orphanRemoval = true, por lo que al eliminar una vacante sus solicitudes en cascada también se eliminarán.
  - OneToOne -> `OfertaDetalle` (columna `id_detalle`): cada vacante tiene exactamente un detalle de oferta. Se usa cascade ALL y orphanRemoval; la relación es LAZY y opcional = false.
  - ManyToMany -> `Categoria` mediante tabla intermedia `vacante_categoria` (columnas `vacante_id`, `categoria_id`).

2) Categoria
- Tabla: `categoria` (entidad `Categoria`)
- Campos: `id`, `categoryName`, `description`.
- Relaciones:
  - ManyToMany (mappedBy = "categories") con `Vacantes` (relación inversa).

3) Solicitudes
- Tabla: `solicitudes` (entidad `Solicitudes`)
- Campos: `id`, `requestDate`, `file`, `comment`.
- Relaciones:
  - ManyToOne -> `Vacantes` (columna `vacante_id`): cada solicitud pertenece a una vacante.
  - ManyToOne -> `Estado` (columna `estado_id`): cada solicitud también tiene un estado.
  - Nota: la relación con `Usuarios` está comentada en el código; si se activa, una solicitud podría apuntar al usuario que aplica.

4) OfertaDetalle
- Tabla: `oferta_detalle` (entidad `OfertaDetalle`)
- Contiene campos descriptivos como `requirements`, `responsibilities`, `benefits`, `location`, `startHour`, `endHour`, `processSelection`, `salary`.
- Relación OneToOne con `Vacantes` (la columna en `vacantes` es `id_detalle` que referencia `oferta_detalle.id`).

5) Usuarios
- Tabla: `usuarios` (entidad `Usuarios`)
- Campos importantes: `idUsuario`, `name`, `lastname`, `email`, `password`, `username`, `dateRegister`, `birthDate`, `jobTitle`, `phone`, `certifications`, flags de cuenta (enabled, accountNonExpired, ...).
- Relaciones:
  - ManyToMany -> `Roles` mediante la tabla intermedia `user_roles` (columnas `user_id`, `role_id`).
  - ManyToOne -> `Estado` (columna `estado_id`): el usuario tiene un estado.
- Lógica adicional: en `@PrePersist` se establece `dateRegister` y, si el `estado` es null, se asigna un estado por defecto `EstadoType.ACTIVO`.

6) Roles y Permissions
- `Roles` (tabla `roles`): tiene un campo `rolesEnum` que indica el tipo de rol (ADMIN, USER, SuperVisor).
- `Permission` (tabla `permissions`): lista de permisos con campo `name` único.
- Relación ManyToMany entre `Roles` y `Permission` definida mediante la tabla `role_permissions`.
- Relación ManyToMany entre `Usuarios` y `Roles` mediante `user_roles`.

7) Estado y EstadoType
- `Estado` contiene un campo `type` de tipo `EstadoType` (enum con valores: ACTIVO, INACTIVO, PENDIENTE, SUSPENDIDO).
- `Estado` se usa como referencia (ManyToOne) desde `Vacantes`, `Solicitudes` y `Usuarios`.

Cómo se traducen estas relaciones a la base de datos
----------------------------------------------------
- ManyToOne: se crea una columna FK en la tabla que contiene la referencia (ej. `vacante.estado_id` apunta a `estado.id`).
- OneToMany (inverso de ManyToOne): no crea tabla extra; es la vista inversa sobre la FK.
- OneToOne: la columna FK se crea en la entidad que contiene `@JoinColumn` (aquí `vacantes.id_detalle` apunta a `oferta_detalle.id`).
- ManyToMany: se crean tablas intermedias explícitas (`vacante_categoria`, `user_roles`, `role_permissions`).

Comportamientos importantes y convenciones del código
----------------------------------------------------
- Cascades y orphanRemoval:
  - `Vacantes.requests` usa cascade = ALL y orphanRemoval = true — elimina solicitudes relacionadas si se borra la vacante.
  - `Vacantes.detail` (OneToOne) también usa cascade ALL y orphanRemoval = true — el detalle de oferta se elimina junto a la vacante.
  - Para ManyToMany se usan cascades limitados (MERGE, PERSIST) en `Usuarios.roles` y `Roles.permissionList` para evitar eliminaciones no deseadas en tablas compartidas.
- Fetch types:
  - Varias relaciones ManyToOne están en EAGER (Vacantes->Estado, Solicitudes->Vacantes, Solicitudes->Estado), lo que carga la referencia inmediatamente.
  - ManyToMany en `Usuarios.roles` y `Roles.permissionList` están en LAZY por defecto.
- Valores por defecto:
  - `Usuarios.prePersist()` establece `dateRegister = new Date()` y un `Estado` por defecto si no existe.

Diagrama ER (simple, ASCII)
--------------------------
Vacantes --< Solicitudes       Vacantes --1-- OfertaDetalle
   |                               \
   |--< vacante_categoria >-- Categoria
   |
   `-- Estado (ManyToOne)

Usuarios --< user_roles >-- Roles --< role_permissions >-- Permission
   `-- Estado (ManyToOne)

Notas para desarrollo local
---------------------------
1. Asegura PostgreSQL en `localhost:5432` con la base `empleos`.
2. Actualiza `src/main/resources/application.properties` si usas otras credenciales.
3. Comandos útiles (Windows):

   - Empaquetar: `mvnw.cmd -DskipTests package`
   - Ejecutar: `mvnw.cmd spring-boot:run`
   - Ejecutar jar (después de empaquetar): `java -jar target\Empleos-Backend-0.0.1-SNAPSHOT.jar`

Pruebas y verificación
----------------------
- La aplicación usa `spring.jpa.hibernate.ddl-auto=create-drop` por defecto (en `application.properties`), lo que recrea el esquema en cada ejecución. En producción, cambia esto a `validate` o `none`.

Siguientes pasos recomendados
----------------------------
- Documentar endpoints en `presentation/controller` (añadir ejemplos de JSON para crear Vacantes, Solicitudes y Usuarios).
- Habilitar la relación Solicitudes->Usuarios si quieres llevar la relación de quién aplica a cada solicitud.
- Añadir README de API o integrar Swagger/OpenAPI para documentación automática.

Contacto
--------
Para dudas sobre el modelado o cambios en las relaciones, abre un issue o un PR y describe el caso de uso objetivo para ajustar cascades/ fetch / FK.

