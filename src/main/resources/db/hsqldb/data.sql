INSERT INTO CONFIG(salario_minimo, num_max_notificaciones) VALUES(900.00, 10);

INSERT INTO ROLES(name) VALUES('ROLE_ADMIN');
INSERT INTO ROLES(name) VALUES('ROLE_DEPENDIENTE');
INSERT INTO ROLES(name) VALUES('ROLE_ENCARGADO');

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

-- PRODUCTOS TOTALMENTE IMPLEMENTADOS!