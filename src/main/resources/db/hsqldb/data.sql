INSERT INTO CONFIG(salario_minimo, num_max_notificaciones, factura_emitida_seq, factura_recibida_seq, factura_emitida_rect_seq, factura_recibida_rect_seq) VALUES(900.00, 10, 4, 16, 0, 0);

INSERT INTO ROLES(name) VALUES('ROLE_ADMIN');
INSERT INTO ROLES(name) VALUES('ROLE_DEPENDIENTE');
INSERT INTO ROLES(name) VALUES('ROLE_ENCARGADO');
INSERT INTO ROLES(name) VALUES('ROLE_TRANSPORTISTA')


INSERT INTO ALMACENES(direccion, capacidad) VALUES ('Calle Ligastorro nº 9', 6000.00);
INSERT INTO ALMACENES(direccion, capacidad) VALUES ('Avenida Gresur edificio AG', 5000.00);

INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('AZULEJOS', 650.00,2);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('BAÑOS', 550.00,1);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('CALEFACCION', 600.00,1);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('LADRILLOS', 594.00,1);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('PINTURAS', 543.00,2);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('REVESTIMIENTOS', 510.00,2);
INSERT INTO ESTANTERIAS(categoria, capacidad, almacen_id) VALUES ('SILICES', 498.00,2);


INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Azulejo Kenia de pasta blanca blanco', 'Azulejo de revestimiento fabricado en pasta blanca con un acabado en color blanco mate de efecto liso y bordes rectificados para juntas finas (de 2 mm de anchura). Ideal para decorar a tu gusto las paredes de todo tipo de estancias interiores. El paquete contiene 4 baldosas, que cubren una superficie de 1,08 m².',
		'M2',100, 15, 'https://assets.leroymerlin.es/is/image/lmes/82359365-0100/reves-30x90-ng-kenia-white-rect-mat.png', 17.99, 15.00, 0.9, 0.3, 0.01, 6.00, 1);

INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Azulejo Shoko blanco rectificado brillo', 'Azulejo de revestimiento fabricado en pasta blanca con un acabado en color blanco brillante de efecto liso y bordes rectificados para juntas finas (de 2 mm de anchura). Ideal para decorar a tu gusto las paredes de todo tipo de estancias interiores. El paquete contiene 6 baldosas, que cubren una superficie de 1,08 m².',
		'M2',80, 10, 'https://assets.leroymerlin.es/is/image/lmes/82359366-0100/reves-30x60-ng-shoko-white-rect-br.png', 12.99, 10.00, 0.6, 0.3, 0.01, 4.00, 1);

INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Azulejo Andros cold', 'Azulejo Cold para decoración interior',
		'M2',150, 20, 'https://assets.leroymerlin.es/is/image/lmes/81928571-0100/decorado-25x50-andros-cold.png', 14.99, 12.00, 0.25, 0.5, 0.0095, 5.00, 1);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Revestimiento Bellapietra Pulpis mate ARTENS', 'Azulejo decorativo de la serie Bellagio Mate, con acabado en color gris, fabricado con pasta blanca, ideal para actualizar el aspecto de tus paredes o renovar el alicatado con este material cerámico de primera calidad.',
		'M2',200, 30, 'https://assets.leroymerlin.es/is/image/lmes/19547745-0100/rev-bellapietra-30x60-pulpis-mate-artens.jpg', 19.99, 15.00, 0.6, 0.3, 0.0104, 5.00, 1);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Revestimiento PARED-ARGILA-AZUL', 'La serie ARGILA AZUL se caracteriza por ser un azulejo de pasta blanca, con formato pequeño 10x10 y acabado en brillo. Además, son piezas con relieve un espesor de 9mm y poseen un contorno irregular. Este tipo de producto imita al azulejo marroquí (zellige), consiguiendo ese toque etnico.',
		'M2',186, 23, 'https://assets.leroymerlin.es/is/image/lmes/82363232-0100/argila-azul-10x10.jpg', 36.99, 30.00, 0.1, 0.1, 0.009, 7.00, 1);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Lavabo con pedestal Charm', 'Lavabo pedestal cuadrado fabricado en material cerámico en color blanco con rebosadero y perforación para grifería. Incluye sifón',
		'UNIDADES',20, 5, 'https://assets.leroymerlin.es/is/image/lmes/81989289-0100/lavabo-pedestal-charm.png', 98.99, 90.00, 0.71, 0.43, 0.48, 10.00, 2);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Lavabo Zeus blanco', 'Lavabo suspendido con encimera, fabricado en piedra natural de color blanco. Cuenta con un seno en el centro y un toallero integrado. Gracias a su diseño minimalista y la perfecta integración de sus elementos, es una solución ideal para aportar a tu baño mayor amplitud. Escuadras de fijación no incluidas.',
		'UNIDADES',5, 2, 'https://assets.leroymerlin.es/is/image/lmes/17956505-0100/zeus-lav-cen-81x46-perf-12cm-blanco.jpg', 399.99, 360.00, 0.81, 0.12, 0.46, 11.00, 2);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Bidé blanco The Gap ROCA', 'Bide ROCA The Gap de color blanco, fabricado en porcelana.',
		'UNIDADES',15, 4, 'https://assets.leroymerlin.es/is/image/lmes/16162524-0100/bide-the-gap-btw-blanco.jpg', 80.99, 70.00, 0.35, 0.54, 0.54, 12.00, 2);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Bañera isla Stori blanco brillo', 'Bañera isla fabricada en resina antideslizante y suave al tacto en color blanco brillo y forma ovalada con un diseño original. Cuenta con una capacidad hasta rebosadero de 210 litros, con orificio de vaciado en un extremo de la bañera. Es muy fácil de instalar, sin necesidad de faldón o patas, y es perfecta para baños grandes. Decora tu baño de diseño con esta bañera de estilo moderno para disfrutar del momento del baño de forma plena. No incluye grifería.',
		'UNIDADES',3, 1, 'https://assets.leroymerlin.es/is/image/lmes/19284783-0100/banera-exenta-stori-156x71-bl-brillo.png', 990.00, 900.00, 0.7, 0.56, 1.57, 15.00, 2);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Estufa de pellet EIDER BIOMASA Pasillo 8 8,02 kw rojo', 'Estufa de pellet fabricada en acero pintado de color rojo burdeos y 8,02 kW de potencia. Cuenta con una autonomía de 9,28 kW a máxima potencia, programación semanal y calienta estancias de hasta 80 m². La salida de humos está en la parte trasera y la de carga en la parte superior. Incluye mando a distancia. Medidas: 85,5 x 88,5 x 30 cm (ancho x alto x profundo). Diámetro del conducto de entrada de aire: 5 cm. Diámetro de salida: 8 cm.',
		'UNIDADES',3, 1, 'https://assets.leroymerlin.es/is/image/lmes/81974063-0100/estufa-pellet-pasillo-8.png', 995.00, 900.00, 0.855, 0.885, 0.3, 17.00, 3);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Estufa de pellet canalizable WATT 10 kw negro/blanco', 'Estufa de pellets canalizable de color blanco fabricada en acero. Dispone de 10 kW de potencia y es capaz de calentar 3 estancias o superficies de hasta 100 m² con 6,5 - 22 h de autonomía. Salida de humos trasera con un diámetro de 80 mm. Rendimiento: 87%. Depósito: 13 kg. Hasta 2 metros de alcance de los ventiladores. Canalizable con un ventilador independiente. Mando a distancia incluido. Medidas: 103 x 100,3 x 36,4 cm (ancho x alto x fondo).',
		'UNIDADES',2, 1, 'https://assets.leroymerlin.es/is/image/lmes/81873497-0100-1/estufa-pellet-watt-reducida-canal-10kw.png', 1299.00, 1210.00, 1.03, 1.003, 0.364, 20.00, 3);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Insert de leña LACUNZA Siena 700', 'Conforme con la nueva normativa Ecodiseño 2022, con muy bajas emisiones. Insert de leña de 10W fabricado en acero vermiculita, con un consumo de 2,9 kg/h. Su diseño de colocación frontal y apertura de puerta lateral, dispone de un tubo de evacuación de humo de 150 mm de diámetro. La superficie recomendada para conseguir un confort y calidez aptos para los meses más fríos es de 90 m². La medida recomendada de la leña es de 60 cm. Clase energética: A. Medidas: 70 x 62 x 45 cm (ancho x alto x profundidad)',
		'UNIDADES',2, 1, 'https://assets.leroymerlin.es/is/image/lmes/82675791-0101/insert-lena-lacunza-siena-700-eco.jpg', 849.00, 800.00, 0.7, 0.62, 0.45, 16.00, 3);

INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Pellet NATURPELLET', 'Saco de pellets, combustible natural que no produce olores y genera un alto poder calorífico con muy bajos niveles de CO₂, lo que contribuye a reducir las emisiones de gases perjudiciales al medioambiente. Está compuesto por madera de pino silvestre 100% descortezada y cuenta con las certificaciones EN Plus A1, que garantiza que se ha utilizado madera virgen, sin tratamientos químicos, y PEFC, que indica que proviene de bosques gestionados de forma sostenible. Contenido del saco: 15 kg',
		'SACOS',50, 7, 'https://assets.leroymerlin.es/is/image/lmes/17868151-0100/saco-pellets-naturpellet-15kg.png', 4.49, 2.00, 0.60, 0.40, 0.1, 15.00, 3);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('pellets KINGFOC', 'Saco de pellets, que generan un alto poder calorífico en los hogares o estufas compatibles que generan gran energía natural. Estos pellets producen muy poco CO₂ cuando se consumen y ayudan a reducir la emisión de gases perjudiciales. Además no producen olores y no afectarán a tu salud. Cuenta con certificado PEFC, que garantiza que el material ha sido obtenido en bosques gestionados de una forma sostenible y respetuosa con el medioambiente. El saco contiene 15 kg.',
		'SACOS',46, 6, 'https://assets.leroymerlin.es/is/image/lmes/17919650-0100/saco-pellet-15-kg-kingfoc-novalia.png', 4.49, 2.50, 0.60, 0.40, 0.1, 15.00, 3);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Bloque de hormigón celular YTONG', 'Bloque de hormigón celular de hormigón celular para construcción marca YTONG. Este producto se obtiene a base de arena de sílice, cemento, cal y un agente de expansión. Gracias a esta proceso de fabricación se obtiene un producto con el que podemos construir muros de carga o cerramiento con un peso mínimo, manipulable facilmente por una sola persona. Medidas: 10 x 25 x 62,5 cm (ancho x alto x largo).',
		'UNIDADES',400, 30, 'https://assets.leroymerlin.es/is/image/lmes/14833903-0100/bloque-hormigon-celular-62-5x25x10cm.png', 2.99, 1.50, 0.1, 0.25, 0.625, 0.7, 4);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Ladrillo Jaen blanco', 'Ladrillo para construir tabiques fabricado en acabado split (irregular) y en color blanco. Medidas: 12 x 5 x 24 cm (ancho x alto x largo)',
		'UNIDADES',432, 30, 'https://assets.leroymerlin.es/is/image/lmes/19723452-0100-1/ladrillo-jaen-5x12x24-blanco.png', 0.75, 0.2, 0.12, 0.05, 0.24, 0.7, 4);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Picholin refractario de cerámica 5', 'Picholin refractario de cerámica para construcción. Medidas: 5,5 x 3 x 22 cm (ancho x alto x largo).',
		'UNIDADES',260, 30, 'https://assets.leroymerlin.es/is/image/lmes/10394545-0100/picholin-re22x5-5x30.png', 0.72, 0.40, 0.055, 0.03, 0.22, 0.7, 4);	
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Ladrillo refractario de cerámica 11', 'Ladrillo refractario de cerámica para construcción. Medidas: 11 x 2 x 22 cm (ancho x alto x largo).',
		'UNIDADES',290, 30, 'https://assets.leroymerlin.es/is/image/lmes/10394503-0100/ladrillo-refractario-22x11x2-r40-9-h.png', 0.87, 0.44, 0.11, 0.02, 0.22, 0.7, 4);	
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Ladrillo cara vista marrón Asturias 11', 'Ladrillo de cara vista (lcv) en color marrón estilo Asturias de cerámica para construcción. Medidas: 11,4 x 5 x 23,6 cm (ancho x alto x largo).',
		'UNIDADES',287, 30, 'https://assets.leroymerlin.es/is/image/lmes/81944557-0100/lcv-rojo-raspado-hidrofugado-24x11-6x4-8.png', 0.39, 0.20, 0.114, 0.05, 0.236, 0.7, 4);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Ladrillo cara vista gris otero 11', 'Ladrillo de cara vista (lcv) en color gris estilo otero de cerámica para construcción. Medidas: 11,4 x 5 x 23,6 cm (ancho x alto x largo).',
		'UNIDADES',303, 30, 'https://assets.leroymerlin.es/is/image/lmes/81944555-0100/lcv-gris-escorial-23-6x11-4x5-cm.png', 0.39, 0.20, 0.114, 0.05, 0.236, 0.7, 4);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Ladrillo cara vista negro Orotava 11', 'Ladrillo de cara vista perforado de arcilla cocida negro, modelo Orotava. Perfecto para muros que requieran un aporte estético en construcción de vivienda. Medidas: 5 x 11,4 x 23,6 cm (ancho x alto x largo).',
		'UNIDADES',309, 30, 'https://assets.leroymerlin.es/is/image/lmes/81944553-0100/lcv-negro-orotava-23-6x11-4x5-cm.png', 0.39, 0.20, 0.114, 0.05, 0.236, 0.7, 4);	
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Pintura BRUGUER Bienestar 4L blanco', 'Pintura acrílica de color blanco con acabado mate, perfecta para decorar las paredes de tu hogar y crear así un ambiente a tu gusto. Para conseguir un acabado perfecto solo necesitarás aplicar una capa con máxima cubrición y máxima lavabilidad. El bote contiene 4 litros de pintura con el que podrás cubrir hasta 56 m². Su conservante antimoho evitas la aparición de humedades y antibacteriano. Cuenta con certificación Ecolabel ya que contiene componentes naturales y ecológicos, además de contar con envase reciclado y reciclable. Gracias a su bajo olor es idónea para pintar las paredes de tu casa sin necesidad de salir de ella. Venta exclusiva en Leroy Merlin. Disponible tester de 30ml del mismo color que esta pintura para que puedas hacer una prueba antes en tu pared y así confirmar que es el color que deseas.',
		'LATAS',70, 5, 'https://assets.leroymerlin.es/is/image/lmes/82511974-0100-1/pint-bienestar-4l-blanco.jpg', 29.99, 24.00, 0.10, 0.30, 0.20, 5.27, 5);	
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Pintura BRUGUER Bienestar 4L verde amanecer', 'Pintura acrílica de color verde amanecer con acabado mate, perfecta para decorar las paredes de tu hogar y crear así un ambiente a tu gusto. Para conseguir un acabado perfecto solo necesitarás aplicar una capa. El bote contiene 4 litros de pintura con el que podrás cubrir hasta hasta 56 m2. Pintura de máxima cubrición y máxima lavabilidad. Con conservante antimoho y antibacteriano. Ecológica, con certificación Ecolabel. Bajo olor, es idónea para pintar las paredes de tu casa sin necesidad de salir de ella. Venta exclusiva en Leroy Merlin. Envase reciclado y reciclable. Disponible tester de 30ml del mismo color que esta pintura para que puedas hacer una prueba antes en tu pared y asi confirmar que es el color que deseas.',
		'LATAS',50, 8, 'https://assets.leroymerlin.es/is/image/lmes/82511961-0100-1/pint-bienestar-4l-verde-amanecer.jpg', 29.99, 24.00, 0.10, 0.30, 0.20, 5.27, 5);	
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Pintura Triple acción TITANLUX 4L pétalo', 'Pintura acrílica de color pétalo con acabado mate, perfecta para crear un ambiente a tu gusto dentro de tu hogar. El bote contiene 4 litros de pintura. Pintura con muy buena cubrición y muy buena lavabilidad. Con conservante antimoho. Ecológica, con certificación Ecolabel. Bajo olor, es idónea para pintar las paredes de tu casa sin necesidad de salir de ella. Venta exclusiva en Leroy Merlin. Envase reciclado y reciclable. Disponible tester de 75ml del mismo color que esta pintura para que puedas hacer una prueba antes en tu pared y asi confirmar que es el color que deseas.',
		'LATAS',34, 6, 'https://assets.leroymerlin.es/is/image/lmes/82495216-0100/pint-3accion-tlux-4l-petalo.png', 26.99, 22.00, 0.13, 0.34, 0.22, 6.00, 5);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Pintura BRUGUER Bienestar 4L azul cristalino', 'Pintura acrílica de color azul cristalino con acabado mate, perfecta para decorar las paredes de tu hogar y crear así un ambiente a tu gusto. Para conseguir un acabado perfecto solo necesitarás aplicar una capa con máxima cubrición y máxima lavabilidad. El bote contiene 4 litros de pintura con el que podrás cubrir hasta 56 m². Su conservante antimoho evitas la aparición de humedades y antibacteriano. Cuenta con certificación Ecolabel ya que contiene componentes naturales y ecológicos, además de contar con envase reciclado y reciclable. Gracias a su bajo olor es idónea para pintar las paredes de tu casa sin necesidad de salir de ella. Venta exclusiva en Leroy Merlin. Disponible tester de 30ml del mismo color que esta pintura para que puedas hacer una prueba antes en tu pared y así confirmar que es el color que deseas.',
		'LATAS',22, 7, 'https://assets.leroymerlin.es/is/image/lmes/82511975-0100-1/pint-bienestar-4l-azul-cristalino.jpg', 29.99, 24.00, 0.10, 0.30, 0.20, 5.27, 5);	
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Pintura con conservante antimoho ALP 4L', 'Pintura Su conservante antimoho evitas la aparición de humedades de color blanco indicada para utilizar en entornos húmedos de exterior o interior. Proporciona un acabado mate de larga duración y un rendimiento de 8-12 m². Contenido: 4 litros.',
		'LATAS',25, 7, 'https://assets.leroymerlin.es/is/image/lmes/13813485-0100-1/pintura-con-conservante-antimoho-alp-4l.png', 35.99, 30.00, 0.18, 0.33, 0.26, 6.00, 5);		
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Pintura Antihumedad LUXENS 2.5 l blanco', 'Pintura antihumedad a base de resina acrílica y de color blanco adecuada para todas las superficies del edificio pintadas o no, incluidas las superficies alcalinas así como las húmedas sin goteos. Indicada para aplicar con brocha o rodillo con un tiempo de secado completo de 12 horas. Bloquea la humedad contenida en la pared y consolida las paredes quebradizas interiores. Permite cubrir las aureolas de humedad de las paredes no enterradas y es transpirable. Evita la aparición de humedades y moho. Tiene un rendimiento de 10 m²/litro. Contenido: 2,5 litros.',
		'LATAS',20, 7, 'https://assets.leroymerlin.es/is/image/lmes/19381474-0100/pintura-antihumedad-luxens-2-5l-bl.png', 19.29, 15.00, 0.15, 0.3, 0.2, 3.1, 5);	
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Revestimiento de partículas minerales serie Decor Dreams luxor dark', 'Revestimiento de pared de color marrón oscuro Luxor dark mate con superficie en relieve Real Touch y biseles pintados a juego. Se instala fácil y rápidamente mediante autoadhesivo ya que un m² puede estar colocado en solo 10 minutos gracias a su sistema de espuma adhesiva para paredes irregulares o muy porosas. Cumple con la normativa de fuego en instalaciones comerciales, dispone de una buena absorción acústica y térmica, y es resistente a golpes y a la humedad. Cuenta con 12 años de garantía. El contenido del paquete permite cubrir una superficie de 1,86 m². Medidas: 30,5 x 61 x 0,68 cm (ancho x alto x grosor).',
		'M2',15, 4, 'https://assets.leroymerlin.es/is/image/lmes/81955983-0100-1/rev-pared-mineral-luxor-dark-61x31cm.png', 29.99, 25.00, 0.305, 0.61, 0.0068, 3.00, 6);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Revestimiento de pared PVC ARTENS alveolar blanco', 'Revestimiento de pared PVC en color blanco con acabado mate liso, para colocar en paredes de interior. Cada paquete contiene 5 lamas para cubrir una superficie de 4,87 m². Podrás decorar cualquier pared o rincón de tu hogar de forma sencilla gracias a su fácil instalación mediante encolado, sin necesidad de hacer agujeros o picar la pared. Dispone de una buena absorción acústica y es resistente a la humedad, pudiendo colocarlo en cualquier estancia de tu casa, incluyendo el baño o aseo.',
		'M2',17, 4, 'https://assets.leroymerlin.es/is/image/lmes/81965185-0100/r-pared-pvc-alveo-bl-260x37-5.png', 5.58, 4.00, 0.3, 0.58, 0.0061, 3.4, 6);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Stickers Kitchen gresite blanco', 'Vinilo de temática moderna que simula una pared real de gresite en color blanco y negro. Ideal para situarlo en la cocina y ofrecer una decoración original. Tamaño: XL (aproximadamente 50x50 cm).',
		'M2',10, 2, 'https://assets.leroymerlin.es/is/image/lmes/81865652-0100/sticker-kitchen-gresite-bl-48x68cm.png', 79.99, 75.00, 0.5, 0.5, 0.0065, 3.8, 6);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Gresite Aqua cobalto para piscina', 'Mosaico cuadrado antideslizante adaptado para baño y piscina, fabricado en trama de fibra de vidrio de 40 mm de espesor, en color azul cobalto y acabado brillante. Cuenta con la clase C3 de la norma antideslizante DIN 519097 que garantiza el antideslizamiento de la superficie con una inclinación de hasta 24˚. El paquete contiene 20 baldosas. Medidas de la placa: 31,5 x 31,5 cm (ancho x largo).',
		'M2',50, 7, 'https://assets.leroymerlin.es/is/image/lmes/81945414-0100/gresite-mosaico-aqua-cobalto-2-5x2-5-c3.jpg', 12.99, 9.00, 0.315, 0.315, 0.006, 3.0, 6);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Gresite punto Aqua Celeste teselas', 'Gresite fabricado en vidrio reciclado de 40 mm de espesor, con acabado en esmalte brillante de color azul celeste. Su aspecto gresite es ideal para decorar cualquier suelo o pared de exterior y adaptado también a la piscina. Posee una alta resistencia a los productos de limpieza y al rayado (3 sobre 5). Medidas: 31,5 x 39,6 cm (ancho x largo).',
		'M2',42, 7, 'https://assets.leroymerlin.es/is/image/lmes/81945415-0100/gresite-punto-2-5x2-5-aqua-celeste.jpg', 10.99, 7.50, 0.315, 0.396, 0.006, 3.0, 6);

INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Cemento gris AXTON', 'Cemento en polvo gris AXTON de 1 kg, para todo tipo de albañilerías. Fabricación de morteros y hormigón. El tiempo de elaboración es de 75 minutos. Uso para espacios y estancias tanto en interior como en exterior. Hasta 6 meses de duración de conservación.',
		'SACOS',60, 10, 'https://assets.leroymerlin.es/is/image/lmes/82204373-0100/cemento-gris-axton-1-5-kg.png', 1.66, 0.50, 0.60, 0.40, 0.1, 1.3, 7);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Cemento gris rápido PUMA 20 kg', 'Saco de cemento rápido de 20 kg obtenido de piedra natural sin ningún tipo de aditivos para una gran variedad de usos: colocación de machiembrados',
		'SACOS',67, 10, 'https://assets.leroymerlin.es/is/image/lmes/19730445-0100/saco-cemento-gris-rapido-20kg-puma.jpg', 9.95, 8.50, 0.65, 0.50, 0.12, 20.0, 7);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Mortero seco gris M-7,5 de 25 kg', 'Mortero seco gris indicado para albañilería en general como mampostería, revoques o pavimentos de terrazo. Se recomienda aplicar con temperaturas entre 5°C y 35°C. Se vende en sacos de 25 kg',
		'SACOS',70, 9, 'https://assets.leroymerlin.es/is/image/lmes/17526761-0100/mortero-seco-m-7-5-gris-25-kg.jpg', 2.19, 1.06, 0.57, 0.49, 0.18, 25.0, 7);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Mortero de reparación AXTON 1.5kg', 'Mortero de hormigón de reparación gris AXTON de 5 kg con fraguado rápido por lo que el tiempo de elaboración es de 6 minutos. Dispone de Apto para trabajo de reparaciones, grietas, fisuras y huecos de hasta 5 cm. Ideal para escaleras, fachadas, marcos de puertas y ventanas. Uso para espacios y estancias tanto en interior como en exterior. Hasta 6 meses de duración de conservación.',
		'SACOS',58, 7, 'https://assets.leroymerlin.es/is/image/lmes/82204735-0100/mortero-de-reparacion-axton-1-5kg.png', 2.99, 1.54, 0.6, 0.52, 0.1, 5.0, 7);	
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Hormigón Secon H-25 25 kg', 'Hormigón seco H-25 ideal para trabajos de construcción en general, tales como: pavimentos, pequeñas reparaciones, fábrica de muros, tabiques de hormigón, hormigones para aceras, bordillos, impresos y soleras. Saco de 25 kg.',
		'SACOS',65, 8, 'https://assets.leroymerlin.es/is/image/lmes/81928958-0100/hormigon-secon-h-25-25kg.png', 2.59, 1.5, 0.54, 0.46, 0.1, 25.0, 7);
		
INSERT INTO PRODUCTOS(nombre, descripcion, unidad, stock, stock_seguridad, URL_imagen, precio_venta, precio_compra, alto, ancho, profundo, peso_unitario, estanteria_id) 
		VALUES ('Micro-hormigon ligero estructural 25 kg', 'Micro-hormigón ligero en base arcilla expandida para la realización de recrecidos. Saco de 25 kg',
		'SACOS',19, 5, 'https://assets.leroymerlin.es/is/image/lmes/81931392-0100/micro-hormigon-ligero-estructural-25kg.png', 8.29, 6.78, 0.47, 0.52, 0.13, 25.0, 7);

-- PRODUCTOS FIN


INSERT INTO CLIENTES(ID, NIF, name, email, tlf, direccion) 
		VALUES (1,'75648392B', 'Paco Lopez Castro', 'paclopcas@gmail.com', '787983426', 'Calle Der Cadi Depe nº1 Bloque C Piso 3 Puerta 1 (Cadiz)');

INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (2,'28415689N', 'Juan Martin Santos', 'juamarsan@hotmail.com', '624791246', 'Ronda Iglesia, 44, 49492, Fresno De Sayago(Zamora)');
		
INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (3,'44812413H', 'Luis Olivares Dominguez', 'rnu6e107@journalism.com', '787983426', 'Campo Horno, 51, 18355, Dúdar(Granada)');

INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (4,'22727936A', 'Iris Estevez Arjona', 'eucsdu6d7@netscape.net', '619957551', 'Kalea De España, 66, 46107, Sant Joanet(Valencia)');

INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (5,'39979389F', 'Eugenia Valero Borrego', 'g4zoiz6re@scientist.com', '634970870', 'Praza Catalunya, 2, 20341, Itsasondo(Gipuzkoa)');
		
INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (6,'22264890Q', 'Victoriano Garzon Martin', 'fv7hkct7ac@lycos.es', '675958716', 'Sector Catalunya, 56, 08133, Puig-reig(Barcelona)');
		
INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (7,'26219453M', 'Maria Amparo Ortiz Poveda', 'r7xnew0aup@mail.com', '784043436', 'Placeta Madrid, 36, 37219, Calzada De Valdunciel(Salamanca)');
		
INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (8,'22898584Z', 'Rocio Barbero Carballo', '1sb7zoxsi1@talk21.com', '650116275', 'Parque Iglesia, 44, 32043, Monterrei(Ourense)');
	
INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (9,'17868188V', 'Angeles Mendoza Serrano', 'mmy3xn9w0@unforgettable.com', '638609667', 'Travessera De España, 22, 16390, Fuentelespino De Moya(Cuenca)');
		
INSERT INTO CLIENTES(ID,NIF, name, email, tlf, direccion) 
		VALUES (10,'87074959H', 'Andreu Vega Gil', '26doupca@yahoo.com', '778946555', 'Jardins Nueva, 41, 14091, Villanueva De Córdoba(Córdoba)');	
		
		
INSERT INTO PROVEEDORES(ID, NIF, name, email, tlf, direccion, IBAN) 
	VALUES (1, '26690085B', 'Borja Hernando Canovas', 'lp0nrqb0zr@teacher.com', '691533234', 'Sector Iglesia, 68, 47134, San Pedro De Latarce(Valladolid)', 'ES1120952845103784661664');	

INSERT INTO PROVEEDORES(ID, NIF, name, email, tlf, direccion, IBAN) 
	VALUES (2, '74668939Z', 'Ariadna Herranz Leal', 'sc8s4nm52@netscape.net', '779832710', 'Jardines Mayor, 56, 08755, Corbera De Llobregat(Barcelona)', 'ES4400812634079548975366');

INSERT INTO PROVEEDORES(ID, NIF, name, email, tlf, direccion, IBAN) 
	VALUES (3, '31214241K', 'Juana Maria Esteban Benito', 'brzvaxa9n@caramail.com', '709604603', 'Praza Real, 78, 16665, Monteagudo De Las Salinas(Cuenca)', 'ES5820384225959160407235');

INSERT INTO PROVEEDORES(ID, NIF, name, email, tlf, direccion, IBAN) 
	VALUES (4, '81792929F', 'Jose Miguel Bautista Merino', 'wlatjldp1@btinternet.com', '660754784', 'Plazuela Horno, 14, 28059, Robregordo(Madrid)', 'ES2020850354752503007736');
		
INSERT INTO PROVEEDORES(ID, NIF, name, email, tlf, direccion, IBAN) 
	VALUES (5, '08129428D', 'Esteban Ponce Benito', 'duc17lxqi@lycos.it', '790098033', 'Parque Mayor, 52, 18391, Alamedilla(Granada)', 'ES2320382563632341001623');
	
	
INSERT INTO ADMINISTRADORES(ID, NIF, name, email, tlf, direccion, NSS, image) 
	VALUES (1, '06860594Q', 'Ruben Galvez Vicente', 'j6n97y3gc@lycos.it', '726782748', 'Calle Gresur nº1 El Gastor(Cadiz)', '240685809175', 'https://i.imgur.com/ROcpSPY.png');
		
INSERT INTO ENCARGADOS_ALMACEN(ID, NIF, name, email, tlf, direccion, NSS, image, almacen_id) 
	VALUES (2, '54511574X', 'Jose Daniel Soria Gallardo', 'iud4v6mn2@lycos.nl', '631087202', 'Praza De España, 89, 19905, Toba, La(Guadalajara)', 'ES6420381225374096884004', 'https://img.lovepik.com/element/40094/5994.png_860.png',1);
		
INSERT INTO ENCARGADOS_ALMACEN(ID, NIF, name, email, tlf, direccion, NSS, image, almacen_id) 
	VALUES (3, '14337755S', 'Iria Rios Linares', 'kuftenuidt@scientist.com', '673206955', 'Poblado Nueva, 14, 19652, Atanzón(Guadalajara)', 'ES1620804547325086507513', 'https://img.lovepik.com/element/40094/5994.png_860.png',2);
	
INSERT INTO DEPENDIENTES(ID, NIF, name, email, tlf, direccion, NSS, image) 
	VALUES (4, '64145337Q', 'Jose Javier Muñoz Jimenez', 'u0kvftny@talk21.com', '688128113', 'Via Nueva, 65, 21499, Palos De La Frontera(Huelva)', 'ES7620853657763800490457', 'https://toppng.com/uploads/preview/dependiente-1155104897331wmwr4sz0.png');
	
INSERT INTO TRANSPORTISTAS(ID, NIF, name, email, tlf, direccion, NSS, image) 
	VALUES (5, '50921688X', 'Eduardo Velazquez Rivas', 'p6qf4oya1@blu.it', '684036867', 'Avenida Horno, 95, 41904, Gines(Sevilla)', 'ES8701287783165809615502', 'https://www.conductorserio.com/wp-content/uploads/2015/01/camionero_bolsa_trabajo-1200x1477.png');

INSERT INTO TRANSPORTISTAS(ID, NIF, name, email, tlf, direccion, NSS, image) 
	VALUES (6, '73929968X', 'Agustin Pineda Rey', 'f8s9khjgx@btinternet.com', '672910341', 'Carrera De España, 74, 28661, Valdemanco(madrid)', 'ES8320853328983168372320', 'https://www.conductorserio.com/wp-content/uploads/2015/01/camionero_bolsa_trabajo-1200x1477.png');

	
INSERT INTO USERS(username, password, personal_id)
	VALUES ('josmunjim', '$2a$10$o/vUHNeCeqzrflufkReHVeYhB.leXs9Os/kv3Q.U/GMaQ97kMX.Te', 4);
	

INSERT INTO USER_ROLES(user_id, role_id) VALUES (1,2);
	

INSERT INTO CONTRATOS(nomina, entidad_bancaria, fecha_inicio, fecha_fin, tipo_jornada, personal_id)
	VALUES (2000.00, 'SANTADER', TO_DATE('2015/04/04','YYYY/MM/DD'), TO_DATE('2999/01/02','YYYY/MM/DD'), 'COMPLETA', 1);
		
INSERT INTO CONTRATOS(nomina, entidad_bancaria, fecha_inicio, fecha_fin, tipo_jornada, personal_id)
	VALUES (1240.00, 'CAIXA-BANK', TO_DATE('2017/04/04','YYYY/MM/DD'), TO_DATE('2021/01/02','YYYY/MM/DD'), 'COMPLETA', 2);
		
INSERT INTO CONTRATOS(nomina, entidad_bancaria, fecha_inicio, fecha_fin, tipo_jornada, personal_id)
	VALUES (1100.00, 'BANKIA', TO_DATE('2018/07/09','YYYY/MM/DD'), TO_DATE('2021/04/03','YYYY/MM/DD'), 'PARCIAL', 3);
			
INSERT INTO CONTRATOS(nomina, entidad_bancaria, fecha_inicio, fecha_fin, tipo_jornada, personal_id)
	VALUES (1300.00, 'SANTANDER', TO_DATE('2016/05/04','YYYY/MM/DD'), TO_DATE('2021/09/05','YYYY/MM/DD'), 'COMPLETA', 4);

INSERT INTO CONTRATOS(nomina, entidad_bancaria, fecha_inicio, fecha_fin, tipo_jornada, personal_id)
	VALUES (910.00, 'CAIXA', TO_DATE('2017/02/08','YYYY/MM/DD'), TO_DATE('2021/03/04','YYYY/MM/DD'), 'MEDIA_JORNADA', 5);
		
INSERT INTO CONTRATOS(nomina, entidad_bancaria, fecha_inicio, fecha_fin, tipo_jornada, personal_id)
	VALUES (1300.00, 'CAIXA', TO_DATE('2017/01/01','YYYY/MM/DD'), TO_DATE('2021/04/06','YYYY/MM/DD'), 'COMPLETA', 6);


INSERT INTO NOTIFICACIONES(tipo_notificacion, cuerpo, fecha_hora, emisor_id)
	VALUES ('SISTEMA', 'ATENCION BAJO STOCK DE Azulejo Kenia de pasta blanca blanco', TO_DATE('2018/06/10 19:34:09', 'YYYY/MM/DD HH24:MI:SS'),null);
	
INSERT INTO NOTIFICACIONES(tipo_notificacion, cuerpo, fecha_hora, emisor_id)
	VALUES ('NORMAL', 'ECHAME UNA MANO PARA BAJAR UNOS PELLETS!!', TO_DATE('2020/06/10 10:21:09', 'YYYY/MM/DD HH24:MI:SS'), 2);
	
INSERT INTO NOTIFICACIONES(tipo_notificacion, cuerpo, fecha_hora, emisor_id)
	VALUES ('URGENTE', 'AVERIA EN MI CAMION!!', TO_DATE('2020/05/11 12:23:09', 'YYYY/MM/DD HH24:MI:SS'), 5);


INSERT INTO LINEAS_ENVIADO(leido, notificacion_id, personal_id)
	VALUES (TRUE, 1, 1);
	
INSERT INTO LINEAS_ENVIADO(leido, notificacion_id, personal_id)
	VALUES (FALSE, 1, 2);
	
INSERT INTO LINEAS_ENVIADO(leido, notificacion_id, personal_id)
	VALUES (FALSE, 1, 3);

INSERT INTO LINEAS_ENVIADO(leido, notificacion_id, personal_id)
	VALUES (TRUE, 1, 4);

INSERT INTO LINEAS_ENVIADO(leido, notificacion_id, personal_id)
	VALUES (FALSE, 2, 4);
	
INSERT INTO LINEAS_ENVIADO(leido, notificacion_id, personal_id)
	VALUES (TRUE, 3, 1);
	
	
INSERT INTO EMITIDAS(id, num_factura, esta_pagada, fecha_emision, importe, cliente_id, dependiente_id)
	VALUES (1, NULL, TRUE, NULL, 207.87, 1, 4);

INSERT INTO EMITIDAS(id, num_factura, esta_pagada, fecha_emision, importe, cliente_id, dependiente_id)
	VALUES (2, 'E-1', FALSE, TO_DATE('2020/12/21', 'YYYY/MM/DD'), 92.95, 2, 4);
	
INSERT INTO EMITIDAS(id, num_factura, esta_pagada, fecha_emision, importe, cliente_id, dependiente_id)
	VALUES (3, 'E-2', TRUE, TO_DATE('2020/12/22', 'YYYY/MM/DD'), 497.99, 3, 4);

INSERT INTO EMITIDAS(id, num_factura, esta_pagada, fecha_emision, importe, cliente_id, dependiente_id)
	VALUES (4, 'E-3', TRUE, TO_DATE('2020/12/23', 'YYYY/MM/DD'), 1111.38, 4, 4);
	
INSERT INTO EMITIDAS(id, num_factura, esta_pagada, fecha_emision, importe, cliente_id, dependiente_id)
	VALUES (5, 'E-4', TRUE, TO_DATE('2019/12/24', 'YYYY/MM/DD'), 1157.38, 5, 4);
	
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (6, 'R-1', TRUE, TO_DATE('2019/05/09', 'YYYY/MM/DD'), 150.00, 'REPOSICION_STOCK', 'Reposicion de stock de Azulejo Kenia de pasta blanca blanco', 1);

INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (7, 'R-2', FALSE, TO_DATE('2020/09/15', 'YYYY/MM/DD'), 123.20, 'GASTOS_VEHICULOS', 'Reparacion pinchazo rueda camion', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (8, 'R-3', TRUE, TO_DATE('2020/11/12', 'YYYY/MM/DD'), 59.15, 'PAGO_IMPUESTOS', 'Pago impuestos del trimestre', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (9, 'R-4', TRUE, TO_DATE('2020/07/10', 'YYYY/MM/DD'), 75.00, 'REPOSICION_STOCK', 'Reposicion de stock de Azulejo Kenia de pasta blanca blanco', 1);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (10, 'R-5', TRUE, TO_DATE('2020/12/12', 'YYYY/MM/DD'), 99.20, 'GASTOS_VEHICULOS', 'Reparacion Picaporte puerta trasera', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (11, 'R-6', TRUE, TO_DATE('2020/10/10', 'YYYY/MM/DD'), 29.15, 'PAGO_IMPUESTOS', 'Pago impuestos del trimestre', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (12, 'R-7', TRUE, TO_DATE('2020/08/12', 'YYYY/MM/DD'), 100.00, 'GASTOS_VEHICULOS', 'Pago de Seguro Mapfre', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (13, 'R-8', TRUE, TO_DATE('2020/07/12', 'YYYY/MM/DD'), 140.00, 'GASTOS_VEHICULOS', 'Pago de Seguro Liberty', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (14, 'R-9', TRUE, TO_DATE('2020/09/12', 'YYYY/MM/DD'), 99.00, 'GASTOS_VEHICULOS', 'Pago de Seguro Mutua', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (15, 'R-10', TRUE, TO_DATE('2020/11/12', 'YYYY/MM/DD'), 79.00, 'GASTOS_VEHICULOS', 'Pago de Seguro Pelayo', null);

INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (16, 'R-11', TRUE, TO_DATE('2019/12/12', 'YYYY/MM/DD'), 103.00, 'GASTOS_VEHICULOS', 'ITV de vehiculo', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (17, 'R-12', TRUE, TO_DATE('2020/01/13', 'YYYY/MM/DD'), 98.00, 'GASTOS_VEHICULOS', 'ITV de vehiculo', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (18, 'R-13', TRUE, TO_DATE('2020/02/17', 'YYYY/MM/DD'), 102.40, 'GASTOS_VEHICULOS', 'ITV de vehiculo', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (19, 'R-14', FALSE, TO_DATE('2020/03/01', 'YYYY/MM/DD'), 68.30, 'GASTOS_VEHICULOS', 'ITV de vehiculo', null);
	
INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (20, 'R-15', TRUE, TO_DATE('2019/12/20', 'YYYY/MM/DD'), 107.10, 'GASTOS_VEHICULOS', 'ITV de vehiculo', null);

INSERT INTO RECIBIDAS(id, num_factura, esta_pagada, fecha_emision, importe, concepto, descripcion, proveedor_id)
	VALUES (21, 'R-16', TRUE, TO_DATE('2020/05/07', 'YYYY/MM/DD'), 198.30, 'GASTOS_VEHICULOS', 'ITV de vehiculo', null);


INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (1, 5, 17.99, 1);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (2, 6, 12.99, 1);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (4, 2, 19.99, 1);

	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (2, 2, 12.99, 2);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (3, 2, 14.99, 2);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (5, 1, 36.99, 2);
	
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (6, 1, 98.99, 3);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (7, 1, 399.00, 3);
	
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (8, 1, 990.00, 4);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (12, 3, 4.49, 4);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (13, 4, 4.49, 4);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (14, 5, 2.99, 4);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (15, 100, 0.75, 4);
	
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (9, 1, 995.00, 5);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (16, 20, 0.72, 5);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (17, 50, 0.87, 5);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (18, 70, 0.39, 5);

INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (19, 87, 0.39, 5);

INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (20, 34, 0.39, 5);
	
INSERT INTO LINEAS_FACTURA(producto_id, cantidad, precio, factura_id)
	VALUES (21, 1, 29.99, 5);


INSERT INTO VEHICULOS(matricula, url_imagen, capacidad, tipo_vehiculo, MMA)
	VALUES ('6543MJK', 'https://i.pinimg.com/originals/61/56/21/615621dc25f20260922e993d6bfac872.png', 30.00, 'CAMION', 3500.00);
	
INSERT INTO VEHICULOS(matricula, url_imagen, capacidad, tipo_vehiculo, MMA)
	VALUES ('8456LDX', 'https://i.pinimg.com/originals/61/56/21/615621dc25f20260922e993d6bfac872.png', 28.00, 'CAMION', 3000.00);

INSERT INTO VEHICULOS(matricula, url_imagen, capacidad, tipo_vehiculo, MMA)
	VALUES ('E5798JMC', 'https://images.vexels.com/media/users/3/127764/isolated/preview/44ae2845c5a453b600b68b6e49e85326-icono-log--stico-de-carretilla-elevadora-by-vexels.png'
				,4.00, 'CARRETILLA_ELEVADORA', 7339.00);

INSERT INTO VEHICULOS(matricula, url_imagen, capacidad, tipo_vehiculo, MMA)
	VALUES ('7329MSG', 'https://cdn.pixabay.com/photo/2017/01/23/10/54/van-2002079_960_720.png', 12.00, 'FURGONETA', 3500.00);

INSERT INTO VEHICULOS(matricula, url_imagen, capacidad, tipo_vehiculo, MMA)
	VALUES ('6539LOP', 'https://cdn.pixabay.com/photo/2017/01/23/10/54/van-2002079_960_720.png', 9.00, 'FURGONETA', 2700.00);

INSERT INTO VEHICULOS(matricula, url_imagen, capacidad, tipo_vehiculo, MMA)
	VALUES ('5319KKF', 'https://e7.pngegg.com/pngimages/967/575/png-clipart-crane-crane.png', 25.00, 'GRUA', 9000.00);


INSERT INTO REPARACIONES(fecha_entrada_taller, fecha_salida_taller, factura_recibida_id, vehiculo_id)
	VALUES (TO_DATE('2019/05/09','YYYY/MM/DD'), TO_DATE('2019/07/09','YYYY/MM/DD'), 7, 1);

INSERT INTO REPARACIONES(fecha_entrada_taller, fecha_salida_taller, factura_recibida_id, vehiculo_id)
	VALUES (TO_DATE('2020/12/12','YYYY/MM/DD'), null, 10, 2);

	
INSERT INTO SEGUROS(compania, tipo_seguro, fecha_contrato, fecha_expiracion, factura_recibida_id, vehiculo_id)
	VALUES ('MAPFRE', 'TODORRIESGO', TO_DATE('2020/08/12','YYYY/MM/DD'), TO_DATE('2020/12/01','YYYY/MM/DD'), 12, 1);
	
INSERT INTO SEGUROS(compania, tipo_seguro, fecha_contrato, fecha_expiracion, factura_recibida_id, vehiculo_id)
	VALUES ('LIBERTY', 'TERCEROS', TO_DATE('2020/07/12','YYYY/MM/DD'), TO_DATE('2020/12/01','YYYY/MM/DD'), 13, 2);

INSERT INTO SEGUROS(compania, tipo_seguro, fecha_contrato, fecha_expiracion, factura_recibida_id, vehiculo_id)
	VALUES ('MUTUA', 'TERCEROS_AMPLIADO', TO_DATE('2020/09/12','YYYY/MM/DD'), TO_DATE('2020/12/01','YYYY/MM/DD'), 14, 3);

INSERT INTO SEGUROS(compania, tipo_seguro, fecha_contrato, fecha_expiracion, factura_recibida_id, vehiculo_id)
	VALUES ('PELAYO', 'TODORRIESGO', TO_DATE('2020/11/12','YYYY/MM/DD'), TO_DATE('2020/12/01','YYYY/MM/DD'), 15, 4);


INSERT INTO ITV(fecha, expiracion, resultado, factura_recibida_id, vehiculo_id)
	VALUES (TO_DATE('2019/12/12','YYYY/MM/DD'), TO_DATE('2020/12/12','YYYY/MM/DD'), 'FAVORABLE', 16, 1);
	
INSERT INTO ITV(fecha, expiracion, resultado, factura_recibida_id, vehiculo_id)
	VALUES (TO_DATE('2020/01/13','YYYY/MM/DD'), TO_DATE('2020/11/10','YYYY/MM/DD'), 'DESFAVORABLE', 17, 2);
	
INSERT INTO ITV(fecha, expiracion, resultado, factura_recibida_id, vehiculo_id)
	VALUES (TO_DATE('2020/02/17','YYYY/MM/DD'), TO_DATE('2020/10/21','YYYY/MM/DD'), 'FAVORABLE', 18, 3);
	
INSERT INTO ITV(fecha, expiracion, resultado, factura_recibida_id, vehiculo_id)
	VALUES (TO_DATE('2020/03/01','YYYY/MM/DD'), TO_DATE('2020/09/09','YYYY/MM/DD'), 'FAVORABLE', 19, 4);
	
INSERT INTO ITV(fecha, expiracion, resultado, factura_recibida_id, vehiculo_id)
	VALUES (TO_DATE('2019/12/20','YYYY/MM/DD'), TO_DATE('2020/12/01','YYYY/MM/DD'), 'DESFAVORABLE', 20, 5);
	
INSERT INTO ITV(fecha, expiracion, resultado, factura_recibida_id, vehiculo_id)
	VALUES (TO_DATE('2020/05/07','YYYY/MM/DD'), TO_DATE('2020/11/11','YYYY/MM/DD'), 'NEGATIVA', 21, 6);
	

INSERT INTO PEDIDOS(direccion_envio, estado, fecha_envio, factura_emitida_id, vehiculo_id, transportista_id)
	VALUES ('Calle Der Cadi Depe nº1 Bloque C Piso 3 Puerta 1 (Cadiz)', 'EN_ESPERA', TO_DATE('2021/01/01','YYYY/MM/DD'), 1, NULL, NULL);

INSERT INTO PEDIDOS(direccion_envio, estado, fecha_envio, factura_emitida_id, vehiculo_id, transportista_id)
	VALUES ('Ronda Iglesia, 44, 49492, Fresno De Sayago(Zamora)', 'PREPARADO', TO_DATE('2020/12/23','YYYY/MM/DD'), 2, NULL, 5);
	
INSERT INTO PEDIDOS(direccion_envio, estado, fecha_envio, factura_emitida_id, vehiculo_id, transportista_id)
	VALUES ('Campo Horno, 51, 18355, Dúdar(Granada)', 'PREPARADO', TO_DATE('2021/01/01','YYYY/MM/DD'), 3, NULL, 6);
	
INSERT INTO PEDIDOS(direccion_envio, estado, fecha_envio, factura_emitida_id, vehiculo_id, transportista_id)
	VALUES ('Kalea De España, 66, 46107, Sant Joanet(Valencia)', 'EN_REPARTO', TO_DATE('2021/01/01','YYYY/MM/DD'), 4, 1, 5);
	
INSERT INTO PEDIDOS(direccion_envio, estado, fecha_envio, factura_emitida_id, vehiculo_id, transportista_id)
	VALUES ('Praza Catalunya, 2, 20341, Itsasondo(Gipuzkoa)', 'ENTREGADO', TO_DATE('2019/11/01','YYYY/MM/DD'), 5, 4, 6);


UPDATE ID_Generator SET sequence = 6 WHERE name = 'personal';
		
UPDATE ID_GENERATOR SET sequence = 10 WHERE name = 'clientes';

UPDATE ID_GENERATOR SET sequence = 5 WHERE name = 'proveedores';

UPDATE ID_GENERATOR SET sequence = 21 WHERE name = 'facturas';

--FIN DE LA CARGA DE DATOS