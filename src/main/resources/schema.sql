CREATE TABLE empresa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    fecha_alta DATE NOT NULL,
    fecha_baja DATE
);

CREATE TABLE transferencia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(19, 2) NOT NULL,
    fecha DATE NOT NULL,
    empresa_id BIGINT NOT NULL,
    FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);