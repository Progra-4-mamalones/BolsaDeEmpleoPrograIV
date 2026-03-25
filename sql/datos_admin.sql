USE bolsa_empleo;

INSERT INTO administrador (id, identificacion, clave)
VALUES (1, 'admin123', '1234')
ON DUPLICATE KEY UPDATE
    identificacion = 'admin123',
    clave = '1234';

UPDATE empresa
SET clave = 'empresa123'
WHERE id = 1;

UPDATE oferente
SET clave = 'oferente123'
WHERE id IN (1, 2, 3, 4, 5);

SELECT 'Admin' as tipo, identificacion as usuario, clave FROM administrador
UNION ALL
SELECT 'Empresa', correo, clave FROM empresa
UNION ALL
SELECT 'Oferente', correo, clave FROM oferente WHERE aprobado = true;

