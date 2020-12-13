INSERT INTO CONFIG(salario_minimo, num_max_notificaciones) VALUES(900.00, 10);

INSERT INTO ROLES(name) VALUES('ROLE_ADMIN');
INSERT INTO ROLES(name) VALUES('ROLE_DEPENDIENTE');
INSERT INTO ROLES(name) VALUES('ROLE_ENCARGADO');

INSERT INTO ALMACENES(direccion, capacidad) VALUES ('Calle Ligastorro nº 9', 2000.00);
INSERT INTO ALMACENES(direccion, capacidad) VALUES ('Avenida Gresur edificio AG', 1000.00);

INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('PINTURAS', 400.00,1);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('CEMENTOS', 300.00,1);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('LADRILLOS', 250.00,1);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('AZULEJOS', 350.00,2);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('HERRAMIENTAS', 150.00,2);

INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ();
