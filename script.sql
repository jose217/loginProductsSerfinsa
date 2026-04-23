---JRAMOS
CREATE DATABASE PruebaTecnica;
GO

USE PruebaTecnica;
GO

CREATE TABLE roles (
    id        BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre    VARCHAR(50) NOT NULL UNIQUE
);
GO

CREATE TABLE usuarios (
    id             BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre         VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL, 
    activo         BIT DEFAULT 1,
    fecha_creacion DATETIME DEFAULT GETDATE()
);
GO

CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL,
    rol_id     BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    CONSTRAINT fk_ur_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_ur_rol     FOREIGN KEY (rol_id)     REFERENCES roles(id)
);
GO


CREATE TABLE tipo_producto (
    id     BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);
GO


CREATE TABLE productos (
    id               BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre           VARCHAR(150) NOT NULL,
    descripcion      VARCHAR(500),
    precio           DECIMAL(10,2) NOT NULL CHECK (precio >= 0),
    stock            INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    tipo_producto_id BIGINT NOT NULL,
    activo           BIT DEFAULT 1,
    fecha_creacion   DATETIME DEFAULT GETDATE(),
    fecha_actualizacion DATETIME DEFAULT GETDATE(),
    creado_por       BIGINT,
    CONSTRAINT fk_prod_tipo    FOREIGN KEY (tipo_producto_id) REFERENCES tipo_producto(id),
    CONSTRAINT fk_prod_usuario FOREIGN KEY (creado_por) REFERENCES usuarios(id)
);
GO

---

INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO roles (nombre) VALUES ('ROLE_USER');
GO


INSERT INTO tipo_producto (nombre) VALUES ('Electrónico');
INSERT INTO tipo_producto (nombre) VALUES ('Ropa');
INSERT INTO tipo_producto (nombre) VALUES ('Alimento');
INSERT INTO tipo_producto (nombre) VALUES ('Hogar');
INSERT INTO tipo_producto (nombre) VALUES ('Otro');
GO

INSERT INTO usuarios (nombre, email, password)
VALUES ('Administrador', 'admin@gmail.com',
'$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');


INSERT INTO usuarios (nombre, email, password)
VALUES ('Usuario Demo', 'user@gmail.com',
'$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO1ohk7nNzm');
GO

INSERT INTO usuario_roles (usuario_id, rol_id)
VALUES (1, 1), (1, 2);

INSERT INTO usuario_roles (usuario_id, rol_id)
VALUES (2, 2);
GO

