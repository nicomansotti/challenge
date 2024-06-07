-- Empresas con fecha de hace más de un mes
INSERT INTO empresa (nombre, fecha_alta) VALUES ('Empresa Antigua 1', DATEADD('MONTH', -2, CURRENT_DATE));
INSERT INTO empresa (nombre, fecha_alta) VALUES ('Empresa Antigua 2', DATEADD('MONTH', -3, CURRENT_DATE));

-- Empresas con fecha de adhesión en el último mes
INSERT INTO empresa (nombre, fecha_alta) VALUES ('Empresa Reciente 1', DATEADD('DAY', -10, CURRENT_DATE));
INSERT INTO empresa (nombre, fecha_alta) VALUES ('Empresa Reciente 2', DATEADD('DAY', -5, CURRENT_DATE));

-- Transferencias en la última semana
INSERT INTO transferencia (monto, fecha, empresa_id) VALUES (1000.00, DATEADD('DAY', -7, CURRENT_DATE), 1);
INSERT INTO transferencia (monto, fecha, empresa_id) VALUES (1500.00, DATEADD('DAY', -5, CURRENT_DATE), 2);

-- Transferencias hace más de un mes
INSERT INTO transferencia (monto, fecha, empresa_id) VALUES (2000.00, DATEADD('MONTH', -1, CURRENT_DATE), 3);
INSERT INTO transferencia (monto, fecha, empresa_id) VALUES (2500.00, DATEADD('DAY', -20, CURRENT_DATE), 4);