DROP DATABASE IF EXISTS bolsa_empleo;
CREATE DATABASE bolsa_empleo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bolsa_empleo;

CREATE TABLE administrador (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identificacion VARCHAR(50) NOT NULL,
    clave VARCHAR(255) NOT NULL
);

CREATE TABLE empresa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    localizacion VARCHAR(150),
    correo VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(50),
    descripcion TEXT,
    aprobada BOOLEAN DEFAULT FALSE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE oferente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(100),
    telefono VARCHAR(50),
    correo VARCHAR(150) UNIQUE NOT NULL,
    residencia VARCHAR(150),
    aprobado BOOLEAN DEFAULT FALSE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE curriculum (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    archivo VARCHAR(255) NOT NULL,
    oferente_id BIGINT UNIQUE,
    FOREIGN KEY (oferente_id)
    REFERENCES oferente(id)
    ON DELETE CASCADE
);

CREATE TABLE caracteristica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    padre_id BIGINT,
    FOREIGN KEY (padre_id)
    REFERENCES caracteristica(id)
    ON DELETE SET NULL
);

CREATE TABLE puesto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    empresa_id BIGINT NOT NULL,
    descripcion TEXT NOT NULL,
    salario DECIMAL(10,2),
    tipo_publicacion ENUM('PUBLICO','PRIVADO') DEFAULT 'PUBLICO',
    activo BOOLEAN DEFAULT TRUE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (empresa_id)
    REFERENCES empresa(id)
    ON DELETE CASCADE
);

CREATE TABLE puesto_caracteristica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    puesto_id BIGINT NOT NULL,
    caracteristica_id BIGINT NOT NULL,
    nivel INT CHECK (nivel BETWEEN 1 AND 5),
    UNIQUE (puesto_id, caracteristica_id),
    FOREIGN KEY (puesto_id)
    REFERENCES puesto(id)
    ON DELETE CASCADE,
    FOREIGN KEY (caracteristica_id)
    REFERENCES caracteristica(id)
);

CREATE TABLE oferente_caracteristica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    oferente_id BIGINT NOT NULL,
    caracteristica_id BIGINT NOT NULL,
    nivel INT CHECK (nivel BETWEEN 1 AND 5),
    UNIQUE (oferente_id, caracteristica_id),
    FOREIGN KEY (oferente_id)
    REFERENCES oferente(id)
    ON DELETE CASCADE,
    FOREIGN KEY (caracteristica_id)
    REFERENCES caracteristica(id)
);

CREATE INDEX idx_empresa_correo ON empresa(correo);
CREATE INDEX idx_oferente_correo ON oferente(correo);
CREATE INDEX idx_puesto_empresa ON puesto(empresa_id);
CREATE INDEX idx_puesto_fecha ON puesto(fecha);