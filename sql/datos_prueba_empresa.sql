USE bolsa_empleo;

INSERT INTO empresa (id, nombre, correo, telefono, localizacion, descripcion, aprobada, fecha_registro)
VALUES (1, 'TechCorp Solutions', 'info@techcorp.com', '+34 91 234 5678', 'Madrid, España',
        'Empresa líder en desarrollo de software y soluciones tecnológicas',
        true, NOW())
    ON DUPLICATE KEY UPDATE
                         nombre = 'TechCorp Solutions',
                         correo = 'info@techcorp.com',
                         telefono = '+34 91 234 5678',
                         aprobada = true;

INSERT INTO caracteristica (nombre, padre_id)
VALUES
    ('Java', NULL),
    ('Python', NULL),
    ('JavaScript', NULL),
    ('SQL', NULL),
    ('Spring Boot', NULL),
    ('React', NULL),
    ('Docker', NULL),
    ('AWS', NULL),
    ('Git', NULL),
    ('Comunicación', NULL)
    ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

INSERT INTO puesto (titulo, descripcion, salario, tipo_publicacion, activo, fecha, empresa_id)
VALUES
    ('Desarrollador Senior Java',
     'Buscamos un desarrollador Senior con experiencia en Java y Spring Boot. Responsabilidades: diseño de arquitecturas, mentoring a juniors, optimización de código. Requisitos: 5+ años de experiencia, conocimiento de microservicios, inglés fluido.',
     3500.00, 'PUBLICO', true, NOW(), 1),

    ('Full Stack Developer Python/React',
     'Se requiere desarrollador fullstack con experiencia en Python (Django/FastAPI) y React. Trabajarás en proyectos de data science y web analytics. Ofrecemos trabajo remoto, flexibilidad de horarios y cursos de especialización.',
     2800.00, 'PUBLICO', true, NOW(), 1),

    ('Especialista en DevOps',
     'Buscamos especialista DevOps con experiencia en Docker, Kubernetes y AWS. Responsabilidades: diseño de pipelines CI/CD, automatización de infraestructura, monitoreo y escalabilidad. Conocimiento de IaC (Terraform, CloudFormation).',
     3200.00, 'PRIVADO', true, NOW(), 1),

    ('Desarrollador Backend Junior',
     'Oportunidad para Junior con conocimientos en Java o Python. Mentoring garantizado. Ofrecemos programa de onboarding completo, cursos internos y ambiente colaborativo.',
     1800.00, 'PUBLICO', false, NOW(), 1);

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 1, id, 4 FROM caracteristica WHERE nombre = 'Java'
    ON DUPLICATE KEY UPDATE nivel = 4;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 1, id, 4 FROM caracteristica WHERE nombre = 'Spring Boot'
    ON DUPLICATE KEY UPDATE nivel = 4;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 1, id, 3 FROM caracteristica WHERE nombre = 'SQL'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 1, id, 3 FROM caracteristica WHERE nombre = 'Git'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 1, id, 2 FROM caracteristica WHERE nombre = 'Docker'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 2, id, 3 FROM caracteristica WHERE nombre = 'Python'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 2, id, 3 FROM caracteristica WHERE nombre = 'JavaScript'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 2, id, 3 FROM caracteristica WHERE nombre = 'React'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 2, id, 2 FROM caracteristica WHERE nombre = 'SQL'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 2, id, 2 FROM caracteristica WHERE nombre = 'Git'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 3, id, 4 FROM caracteristica WHERE nombre = 'Docker'
    ON DUPLICATE KEY UPDATE nivel = 4;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 3, id, 4 FROM caracteristica WHERE nombre = 'AWS'
    ON DUPLICATE KEY UPDATE nivel = 4;

INSERT INTO puesto_caracteristica (puesto_id, caracteristica_id, nivel)
SELECT 3, id, 3 FROM caracteristica WHERE nombre = 'Git'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO oferente (nombre, apellido, correo, telefono, residencia, identificacion, nacionalidad, aprobado, fecha_registro)
VALUES
    ('Juan', 'García López', 'juan.garcia@email.com', '+34 644 123456', 'Barcelona, España', '12345678A', 'Española', true, NOW()),
    ('María', 'Rodríguez Santos', 'maria.rodriguez@email.com', '+34 655 234567', 'Madrid, España', '87654321B', 'Española', true, NOW()),
    ('Carlos', 'Martínez Pérez', 'carlos.martinez@email.com', '+34 666 345678', 'Valencia, España', '11111111C', 'Española', true, NOW()),
    ('Laura', 'Gómez Fernández', 'laura.gomez@email.com', '+34 677 456789', 'Bilbao, España', '22222222D', 'Española', false, NOW()),
    ('David', 'López Jiménez', 'david.lopez@email.com', '+34 688 567890', 'Sevilla, España', '33333333E', 'Española', true, NOW());

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 1, id, 4 FROM caracteristica WHERE nombre = 'Java'
    ON DUPLICATE KEY UPDATE nivel = 4;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 1, id, 4 FROM caracteristica WHERE nombre = 'Spring Boot'
    ON DUPLICATE KEY UPDATE nivel = 4;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 1, id, 3 FROM caracteristica WHERE nombre = 'SQL'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 1, id, 3 FROM caracteristica WHERE nombre = 'Git'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 1, id, 2 FROM caracteristica WHERE nombre = 'Docker'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 2, id, 3 FROM caracteristica WHERE nombre = 'Python'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 2, id, 3 FROM caracteristica WHERE nombre = 'JavaScript'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 2, id, 3 FROM caracteristica WHERE nombre = 'React'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 2, id, 2 FROM caracteristica WHERE nombre = 'SQL'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 2, id, 2 FROM caracteristica WHERE nombre = 'Git'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 3, id, 5 FROM caracteristica WHERE nombre = 'Docker'
    ON DUPLICATE KEY UPDATE nivel = 5;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 3, id, 4 FROM caracteristica WHERE nombre = 'AWS'
    ON DUPLICATE KEY UPDATE nivel = 4;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 3, id, 4 FROM caracteristica WHERE nombre = 'Git'
    ON DUPLICATE KEY UPDATE nivel = 4;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 3, id, 2 FROM caracteristica WHERE nombre = 'Python'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 4, id, 2 FROM caracteristica WHERE nombre = 'Java'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 4, id, 2 FROM caracteristica WHERE nombre = 'JavaScript'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 5, id, 3 FROM caracteristica WHERE nombre = 'Java'
    ON DUPLICATE KEY UPDATE nivel = 3;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 5, id, 2 FROM caracteristica WHERE nombre = 'Spring Boot'
    ON DUPLICATE KEY UPDATE nivel = 2;

INSERT INTO oferente_caracteristica (oferente_id, caracteristica_id, nivel)
SELECT 5, id, 2 FROM caracteristica WHERE nombre = 'Git'
    ON DUPLICATE KEY UPDATE nivel = 2;


SELECT * FROM empresa;

SELECT p.id, p.titulo, p.salario, p.activo, e.nombre as empresa
FROM puesto p
         JOIN empresa e ON p.empresa_id = e.id;

SELECT p.titulo, c.nombre, pc.nivel
FROM puesto_caracteristica pc
         JOIN puesto p ON pc.puesto_id = p.id
         JOIN caracteristica c ON pc.caracteristica_id = c.id
ORDER BY p.id, c.nombre;

SELECT id, CONCAT(nombre, ' ', apellido) as nombre, correo, aprobado
FROM oferente
WHERE aprobado = true;

SELECT CONCAT(o.nombre, ' ', o.apellido) as oferente, c.nombre, oc.nivel
FROM oferente_caracteristica oc
         JOIN oferente o ON oc.oferente_id = o.id
         JOIN caracteristica c ON oc.caracteristica_id = c.id
ORDER BY o.id, c.nombre;