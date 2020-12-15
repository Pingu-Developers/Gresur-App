package org.springframework.gresur.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.Unidad;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.ClienteService;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.service.PedidoService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.exceptions.CapacidadProductoExcededException;
import org.springframework.gresur.service.exceptions.StockWithoutEstanteriaException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
public class ProductoTestService {
	
	@Autowired
	protected FacturaEmitidaService facturaEmitidaService;
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected DependienteService dependienteService;
	
	@Autowired
	protected LineasFacturaService lineaFacturaService;
		
	@Autowired
	protected PedidoService pedidoService;
	
	@Autowired
	protected AlmacenService almacenService;
	
	@Autowired
	protected EstanteriaService estanteriaService;
	
	@Autowired
	protected ProductoService productoService;
	
	@Autowired
	protected NotificacionService notificacionService;
	
	@Autowired
	protected AdministradorService administradorService;
	
	@Autowired
	protected ConfiguracionService configuracionService;

	@Autowired
	protected DBUtility util;

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										FUNCIONES DE CARGA DE DATOS PARA LOS TESTS								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@BeforeAll
	@AfterEach
	@Transactional
	void clearDB() {
		util.clearDB();
	}

	@BeforeEach
	@Transactional
	void InitAll() {
		// CREACION DE ALMACEN
		Almacen alm = new Almacen();
		alm.setCapacidad(3000.0);
		alm.setDireccion("Las Columnas - Cadiz");
		almacenService.save(alm);
		
		// CREACION DE ESTANTERIA
		Estanteria est = new Estanteria();
		est.setAlmacen(alm);
		est.setCapacidad(10.0);
		est.setCategoria(Categoria.BAÑOS);
		
		// CREACION DE PRODUCTO
		List<Producto> ls = new ArrayList<Producto>();
		Producto p = new Producto();
		p.setAlto(1.);
		p.setAncho(1.);
		p.setDescripcion("");
		p.setEstanteria(est);
		p.setNombre("Vater inteligente");
		p.setPesoUnitario(80.);
		p.setPrecioCompra(150.);
		p.setPrecioVenta(200.);
		p.setProfundo(1.);
		p.setStock(2);
		p.setStockSeguridad(0);
		p.setUnidad(Unidad.UNIDADES);
		
		ls.add(p);
		est.setProductos(ls);
		
		estanteriaService.save(est);
		productoService.save(p);
		
		// CREACION DE ADMINISTRADOR
		Administrador adm = new Administrador();
		adm.setName("Jose Luis Gresur");
		adm.setNIF("18845878A");
		adm.setEmail("e1@email.com");
		adm.setTlf("696823445");
		adm.setDireccion("Av. Reina Mercedes");
		adm.setNSS("12 1234123890");
		
		administradorService.save(adm);

		// CREACION DE FACTURA EMITIDA
		Cliente cliente = new Cliente();
		cliente.setDireccion("Calle");
		cliente.setEmail("cliente@mail.es");
		cliente.setName("Jose Luis");
		cliente.setNIF("54789663T");
		cliente.setTlf("657412397");
		
		clienteService.save(cliente);
		
		Dependiente dependiente = new Dependiente();
		dependiente.setDireccion("C/ ejemplo");
		dependiente.setEmail("patata@gmail.com");
		dependiente.setImage("/resourdes/foto.png");
		dependiente.setName("Antonio Jose Ruiz Ruiz");
		dependiente.setNIF("12345678L");
		dependiente.setNSS("123456789012");
		dependiente.setTlf("123456789");

		dependienteService.save(dependiente);
				
		Estanteria estanteria = new Estanteria();
		estanteria.setAlmacen(alm);
		estanteria.setCapacidad(550.00);
		estanteria.setCategoria(Categoria.AZULEJOS);
		estanteria = estanteriaService.save(estanteria);
		
		Producto p1 = new Producto();
		p1.setAlto(1.1);
		p1.setAncho(1.9);
		p1.setDescripcion("Una descripcion de un producto");
		p1.setEstanteria(estanteria);
		p1.setNombre("Azulejo rojo");
		p1.setPesoUnitario(80.10);
		p1.setPrecioCompra(10.29);
		p1.setPrecioVenta(29.01);
		p1.setProfundo(1.2);
		p1.setStock(70);
		p1.setStockSeguridad(30);
		p1.setUnidad(Unidad.UNIDADES);
		
		Producto p2 = new Producto();
		p2.setAlto(1.1);
		p2.setAncho(1.9);
		p2.setDescripcion("Una descripcion de un producto");
		p2.setEstanteria(estanteria);
		p2.setNombre("Azulejo amarillo");
		p2.setPesoUnitario(80.10);
		p2.setPrecioCompra(10.29);
		p2.setPrecioVenta(29.01);
		p2.setProfundo(1.2);
		p2.setStock(35);
		p2.setStockSeguridad(30);
		p2.setUnidad(Unidad.UNIDADES);
					
		p1 = productoService.save(p1);
		p2 = productoService.save(p2);
		
		List<Producto> lp = new ArrayList<Producto>();
		lp.add(p1);
		lp.add(p2);	
		
		FacturaEmitida fem = new FacturaEmitida();
		fem.setCliente(cliente);
		fem.setDependiente(dependiente);
		fem.setEstaPagada(true);
		fem.setFecha(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		fem.setImporte(320.15);

		
		fem = facturaEmitidaService.save(fem);
		
		List<LineaFactura> lf = new ArrayList<LineaFactura>();
		LineaFactura lf1 = new LineaFactura();
		LineaFactura lf2 = new LineaFactura();
		lf1.setCantidad(10);
		lf1.setFactura(fem);
		lf1.setPrecio(100.15);
		lf1.setProducto(p1);
		

		lf1 = lineaFacturaService.save(lf1);
		
		lf2.setCantidad(20);
		lf2.setFactura(fem);
		lf2.setPrecio(220.00);
		lf2.setProducto(p2);
						
		lf2 = lineaFacturaService.save(lf2);
						
		Pedido pedido = new Pedido();
		pedido.setDireccionEnvio("C/ Ejemplo");
		pedido.setEstado(EstadoPedido.EN_ESPERA);
		pedido.setFacturaEmitida(fem);
		pedido.setFechaEnvio(LocalDate.parse("14/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		pedidoService.save(pedido);
		
		lf.add(lf1);
		lf.add(lf2);
		fem.setLineasFacturas(lf);
		facturaEmitidaService.save(fem);

		// CREACION DE CONFIGURACION
		Configuracion config = new Configuracion();
		config.setNumMaxNotificaciones(1);
		config.setSalarioMinimo(950.);
		configuracionService.save(config);

	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	* 										FUNCIONES DE LOS TESTS													 *
	* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Encuentra un producto con el nombre exacto -- caso positivo")
	void findProductoByNameComplete() {
		List<Producto> p = productoService.findAllProductosByName("Vater inteligente");
		assertThat(p.size()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	@DisplayName("Encuentra los productos segun el nombre introducido -- caso positivo")
	void findProductoByNameIncomplete() {
		List<Producto> p = productoService.findAllProductosByName("Vater in");
		assertThat(p.size()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	@DisplayName("Encuentra los productos segun el nombre introducido -- caso negativo")
	void findProductosByNameNotFound() {
		List<Producto> p = productoService.findAllProductosByName("Lavabo ROCA");
		assertThat(p.size()).isEqualTo(0);
	}
	

	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
		
	@Test
	@Transactional
	@DisplayName("Actualizar un producto - caso positivo")
	void updateProductoWithoutProblems() {
		Producto p = productoService.findAll().iterator().next();
		p.setStock(2);
		assertThat(productoService.count()).isEqualTo(3);
	}
	@Test
	@Transactional
	@DisplayName("Guardar un nuevo producto - caso positivo")
	void addProductoWithoutProblems() {
		Estanteria est = estanteriaService.findAll().iterator().next();
		Producto p = new Producto();
		p.setAlto(1.);
		p.setAncho(1.);
		p.setDescripcion("otro vater");
		p.setEstanteria(est);
		p.setNombre("Otro vater");
		p.setPesoUnitario(80.);
		p.setPrecioCompra(150.);
		p.setPrecioVenta(200.);
		p.setProfundo(1.);
		p.setStock(2);
		p.setStockSeguridad(0);
		p.setUnidad(Unidad.UNIDADES);
		productoService.save(p);
		assertThat(productoService.count()).isEqualTo(4);
	}

	@Test
	@Transactional
	@DisplayName("RN: La capacidad de el/los productos excede a la capacidad de la estanteria (update) -- caso negativo")
	void updateProductoVolumenExceded() {
		Producto p = productoService.findAll().iterator().next();
		
		assertThrows(CapacidadProductoExcededException.class, () -> {p.setStock(999999);});  //DEBEMOS VALIDAR ESTOS SET DE ALGUNA MANERA, LOS UPDATE SE HACEN DIRECTAMENTE EN EL SET, SIN SAVE
		assertThat(productoService.findById(p.getId())).isNotEqualTo(p);					 //Compronacion de rollback
	}
	
	@Test
	@Transactional
	@DisplayName("RN: La capacidad de el/los productos excede a la capacidad de la estanteria (new) -- caso negativo")
	void addProductoVolumenExceded() {
		Estanteria est = estanteriaService.findAll().iterator().next();
		Producto p = new Producto();
		p.setAlto(1.);
		p.setAncho(1.);
		p.setDescripcion("otro vater");
		p.setEstanteria(est);
		p.setNombre("Otro vater");
		p.setPesoUnitario(80.);
		p.setPrecioCompra(150.);
		p.setPrecioVenta(200.);
		p.setProfundo(1.);
		p.setStock(200000);
		p.setStockSeguridad(0);
		p.setUnidad(Unidad.UNIDADES);
		p.setId(200L);
		
		assertThrows(CapacidadProductoExcededException.class, () -> {productoService.save(p);});
		assertThat(productoService.findById(200L)).isNotEqualTo(p);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: No se puede añadir stock a un producto sin estanteria asociada (new) -- caso positivo")
	void addProductoWithoutEstanteriaWithoutProblems() {
		Producto p = new Producto();
		p.setAlto(1.);
		p.setAncho(1.);
		p.setDescripcion("otro vater");
		p.setNombre("Otro vater");
		p.setPesoUnitario(80.);
		p.setPrecioCompra(150.);
		p.setPrecioVenta(200.);
		p.setProfundo(1.);
		p.setStock(0);
		p.setUnidad(Unidad.UNIDADES);
		productoService.save(p);
		assertThat(productoService.count()).isEqualTo(4);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: No se puede añadir stock a un producto sin estanteria asociada (new) -- caso negativo")
	void addStockToProductoWithoutEstanteria() {
		Producto p = new Producto();
		p.setAlto(1.);
		p.setAncho(1.);
		p.setDescripcion("otro vater");
		p.setNombre("Otro vater");
		p.setPesoUnitario(80.);
		p.setPrecioCompra(150.);
		p.setPrecioVenta(200.);
		p.setProfundo(1.);
		p.setStock(1);
		p.setUnidad(Unidad.UNIDADES);
		p.setId(201L);
		assertThrows(StockWithoutEstanteriaException.class, () -> {productoService.save(p);});
		assertThat(productoService.findById(201L)).isNotEqualTo(p);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: No se puede añadir stock a un producto sin estanteria asociada (update) -- caso negativo")
	void updateStockToProductoWithoutEstanteria() {
		Producto p = new Producto();
		p.setAlto(1.);
		p.setAncho(1.);
		p.setDescripcion("otro vater");
		p.setNombre("Otro vater");
		p.setPesoUnitario(80.);
		p.setPrecioCompra(150.);
		p.setPrecioVenta(200.);
		p.setProfundo(1.);
		p.setStock(0);
		p.setUnidad(Unidad.UNIDADES);
		productoService.save(p);
			
		assertThrows(StockWithoutEstanteriaException.class, () -> {p.setStock(2);});				//DEBEMOS VALIDAR ESTOS SET DE ALGUNA MANERA, LOS UPDATE SE HACEN DIRECTAMENTE EN EL SET, SIN SAVE
		assertThat(productoService.findAllProductosByName("Otro vater").get(0)).isNotEqualTo(p);	//Comprobacion de rollback
	}
	
	@Test
	@Transactional
	@DisplayName("Propiedad derivada: Demanda")
	void checkDemandaProducto() {
		List<Producto> ls = productoService.findAll();
		Double acum = 0.;
		for(Producto p: ls) {
			acum += productoService.getDemanda(p, LocalDate.of(0, 1, 1));
		}
		assertThat(acum).isEqualTo(1);
	}
	
	/* * * * * * * * * * * * * * * *
	 *   FUNCIONALIDADES TESTS     *
	 * * * * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Cuando el stock de un producto está por debajo del de seguridad se lanza notificación -- caso positivo (se lanza)")
	void updateProductoThrowNotificacionAlertaStock() {
		Producto p = productoService.findAll().iterator().next();
		p.setStockSeguridad(10);	
		
		assertThat(notificacionService.findAll().size()).isEqualTo(1);	//NO SE LANZA LA NOTIFICACION PORQUE AL SER UN UPDATE NO PASA POR EL SAVE, DEBERIA LANZARSE EN EL SET
	}
	
	@Test
	@Transactional
	@DisplayName("Cuando el stock de un producto está por debajo del de seguridad se lanza notificación -- caso negativo (no se lanza)")
	void updateProductoThrowNotificacionAlertaStockNegative() {
		Producto p = productoService.findAll().iterator().next();
		p.setStockSeguridad(100);
		
		assertThat(notificacionService.findAll().size()).isEqualTo(0);	
	}
	
}