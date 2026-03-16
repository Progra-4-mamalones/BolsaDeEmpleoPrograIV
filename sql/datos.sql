USE bolsa_empleo;

INSERT INTO administrador (identificacion, clave) VALUES
('admin001', 'admin123'),
('admin002', 'admin456');

INSERT INTO empresa (nombre, localizacion, correo, telefono, descripcion, clave, aprobada) VALUES
('TechCR S.A.', 'San José, Costa Rica', 'contacto@techcr.com', '2200-1111', 'Empresa de desarrollo de software', 'empresa123', TRUE),
('Finanzas del Norte', 'Heredia, Costa Rica', 'rrhh@finanzasnorte.com', '2200-2222', 'Consultora financiera y contable', 'empresa123', TRUE),
('DataSoft Labs', 'Cartago, Costa Rica', 'jobs@datasoft.com', '2200-3333', 'Laboratorio de datos e inteligencia artificial', 'empresa123', TRUE);

INSERT INTO oferente (identificacion, nombre, apellido, nacionalidad, telefono, correo, residencia, clave, aprobado) VALUES
('101110111', 'Carlos', 'Mora', 'Costarricense', '8800-1111', 'carlos.mora@gmail.com', 'Heredia', 'oferente123', TRUE),
('202220222', 'Valeria', 'Jiménez', 'Costarricense', '8800-2222', 'valeria.j@gmail.com', 'San José', 'oferente123', TRUE),
('303330333', 'Diego', 'Ramírez', 'Costarricense', '8800-3333', 'diego.r@gmail.com', 'Alajuela', 'oferente123', FALSE);

INSERT INTO caracteristica (nombre, padre_id) VALUES
('Programación', NULL),
('Bases de Datos', NULL),
('Gestión', NULL),
('Java', 1),
('Python', 1),
('JavaScript', 1),
('MySQL', 2),
('Oracle', 2),
('Liderazgo', 3),
('Trabajo en equipo', 3);

INSERT INTO puesto (titulo, empresa_id, descripcion, salario, tipo_publicacion, activo) VALUES
('Desarrollador Backend Java', 1, 'Desarrollador Backend Java con experiencia en Spring Boot', 1200000.00, 'PUBLICO', TRUE),
('Desarrollador Frontend React', 1, 'Desarrollador Frontend con React y Tailwind', 1000000.00, 'PUBLICO', TRUE),
('Analista Oracle', 2, 'Analista de bases de datos Oracle', 1500000.00, 'PUBLICO', TRUE),
('Científico de Datos', 3, 'Científico de datos con Python y ML', 1800000.00, 'PRIVADO', TRUE),
('Gerente de Proyectos TI', 2, 'Gerente de proyectos TI', 2000000.00, 'PUBLICO', TRUE);

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel) VALUES
(1, 4, 4),
(1, 7, 3),
(2, 6, 4),
(3, 8, 5),
(3, 7, 3),
(4, 5, 5),
(5, 9, 4),
(5, 10, 3);

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel) VALUES
(1, 4, 3),
(1, 7, 4),
(2, 6, 5),
(2, 5, 3),
(3, 8, 2),
(3, 9, 4);