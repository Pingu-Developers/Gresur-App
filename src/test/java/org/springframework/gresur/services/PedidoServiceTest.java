package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.Concepto;
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoSeguro;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.model.Unidad;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.ClienteService;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.PedidoService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.service.exceptions.MMAExceededException;
import org.springframework.gresur.service.exceptions.PedidoConVehiculoSinTransportistaException;
import org.springframework.gresur.service.exceptions.PedidoLogisticException;
import org.springframework.gresur.service.exceptions.UnmodifablePedidoException;
import org.springframework.gresur.service.exceptions.VehiculoDimensionesExceededException;
import org.springframework.gresur.service.exceptions.VehiculoNotAvailableException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
public class PedidoServiceTest {

	@Autowired
	protected DBUtility util;
	
	@Autowired
	protected AlmacenService almacenService;
	
	@Autowired
	protected EstanteriaService estanteriaService;
	
	@Autowired
	protected ProductoService productoService;
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected DependienteService dependienteService;
	
	@Autowired
	protected TransportistaService transportistaService;
	
	@Autowired
	protected FacturaEmitidaService facturaEmitidaService;
	
	@Autowired
	protected LineasFacturaService lineaFacturaService;
	
	@Autowired
	protected PedidoService pedidoService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected FacturaRecibidaService facturaRecibidaService;
	
	@Autowired
	protected ITVService itvService;
	
	@Autowired
	protected SeguroService seguroService;
	
	@Autowired
	protected ConfiguracionService confService;
	

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
		
		//CREACION DE CONFIGURACION
		Configuracion conf = new Configuracion();
		conf.setSalarioMinimo(900.00);
		conf.setNumMaxNotificaciones(100);
		conf.setFacturaEmitidaSeq(0L);
		conf.setFacturaRecibidaSeq(0L);
		conf.setFacturaEmitidaRectSeq(0L);
		conf.setFacturaRecibidaRectSeq(0L);
				
		confService.save(conf);
		
		// CREACION DE ALMACEN
		Almacen almacen = new Almacen();
		almacen.setCapacidad(300000.0);
		almacen.setDireccion("Las Columnas - Cadiz");
		almacen = almacenService.save(almacen);
		
		// CREACION DE ESTANTERIA

		Estanteria estanteria = new Estanteria();
		estanteria.setAlmacen(almacen);
		estanteria.setCapacidad(10000.0);
		estanteria.setCategoria(Categoria.BANOS);
		estanteria = estanteriaService.save(estanteria);
		
		// CREACION DE PRODUCTO
		List<Producto> listaProductosEstanteria = new ArrayList<Producto>();
		
		Producto producto1 = new Producto();
		producto1.setAlto(1.);
		producto1.setAncho(1.);
		producto1.setDescripcion("");
		producto1.setEstanteria(estanteria);
		producto1.setNombre("Vater inteligente");
		producto1.setPesoUnitario(100.);
		producto1.setPrecioCompra(150.);
		producto1.setPrecioVenta(300.);
		producto1.setProfundo(1.);
		producto1.setStock(200);
		producto1.setStockSeguridad(0);
		producto1.setUnidad(Unidad.UNIDADES);
		producto1 = productoService.save(producto1);
		
		Producto producto2 = new Producto();
		producto2.setAlto(1.2);
		producto2.setAncho(0.8);
		producto2.setDescripcion("");
		producto2.setEstanteria(estanteria);
		producto2.setNombre("Vater Tonto");
		producto2.setPesoUnitario(50.);
		producto2.setPrecioCompra(150.);
		producto2.setPrecioVenta(200.);
		producto2.setProfundo(1.);
		producto2.setStock(200);
		producto2.setStockSeguridad(0);
		producto2.setUnidad(Unidad.UNIDADES);
		producto2 = productoService.save(producto2);
		
		Producto producto3 = new Producto();
		producto3.setAlto(1.5);
		producto3.setAncho(1.2);
		producto3.setDescripcion("");
		producto3.setEstanteria(estanteria);
		producto3.setNombre("Vater De IQ medio");
		producto3.setPesoUnitario(80.);
		producto3.setPrecioCompra(150.);
		producto3.setPrecioVenta(250.);
		producto3.setProfundo(0.7);
		producto3.setStock(200);
		producto3.setStockSeguridad(0);
		producto3.setUnidad(Unidad.UNIDADES);
		producto3 = productoService.save(producto3);
		
		listaProductosEstanteria.add(producto1);
		listaProductosEstanteria.add(producto2);
		listaProductosEstanteria.add(producto3);
				
		// CREACION DE CLIENTE
		
		Cliente cliente = new Cliente();
		cliente.setDireccion("Calle");
		cliente.setEmail("cliente@mail.es");
		cliente.setName("Jose Luis");
		cliente.setNIF("54789663T");
		cliente.setTlf("657412397");
		
		cliente = clienteService.save(cliente);
		
		// CREACION DE DEPENDIENTE
		
		Dependiente dependiente = new Dependiente();
		dependiente.setDireccion("C/ ejemplo");
		dependiente.setEmail("patata@gmail.com");
		dependiente.setImage("/resourdes/foto.png");
		dependiente.setName("Antonio Jose Ruiz Ruiz");
		dependiente.setNIF("12345678L");
		dependiente.setNSS("123456789012");
		dependiente.setTlf("123456789");
		
		dependiente = dependienteService.save(dependiente);
		
		// CREACION DE TRANSPORTISTA

		Transportista transportista = new Transportista();
		transportista.setName("Jose Luis Gresur");
		transportista.setNIF("18845878A");
		transportista.setEmail("e1@email.com");
		transportista.setTlf("696823445");
		transportista.setDireccion("Av. Reina Mercedes");
		transportista.setNSS("12 1234123890");
		transportista.setImage("/resources/foto.png");
		
		transportista = transportistaService.save(transportista);

		// CREACION DE FACTURA EMITIDA
		
		FacturaEmitida facturaPedido1 = new FacturaEmitida();
		facturaPedido1.setCliente(cliente);
		facturaPedido1.setDependiente(dependiente);
		facturaPedido1.setEstaPagada(true);
		facturaPedido1.setImporte(320.15);
		
		facturaPedido1 = facturaEmitidaService.save(facturaPedido1);
		
		FacturaEmitida facturaPedido2 = new FacturaEmitida();
		facturaPedido2.setCliente(cliente);
		facturaPedido2.setDependiente(dependiente);
		facturaPedido2.setEstaPagada(true);
		facturaPedido2.setImporte(320.15);
		
		facturaPedido2 = facturaEmitidaService.save(facturaPedido2);
		
		FacturaEmitida facturaPedido3 = new FacturaEmitida();
		facturaPedido3.setCliente(cliente);
		facturaPedido3.setDependiente(dependiente);
		facturaPedido3.setEstaPagada(true);
		facturaPedido3.setImporte(320.15);
		
		facturaPedido3 = facturaEmitidaService.save(facturaPedido3);

		// CREACION DE LINEA FACTURA
		
		List<LineaFactura> lineasFacturaPedido1 = new ArrayList<LineaFactura>();
		
		LineaFactura lf1 = new LineaFactura();
		lf1.setFactura(facturaPedido1);
		lf1.setProducto(producto1);
		lf1.setCantidad(10);
		lf1.setPrecio(producto1.getPrecioVenta()*lf1.getCantidad());
		
		lf1 = lineaFacturaService.save(lf1);
		
		LineaFactura lf2 = new LineaFactura();
		lf2.setFactura(facturaPedido1);
		lf2.setProducto(producto2);
		lf2.setCantidad(10);
		lf2.setPrecio(producto2.getPrecioVenta()*lf2.getCantidad());
		
		lf2 = lineaFacturaService.save(lf2);
		
		lineasFacturaPedido1.add(lf1);
		lineasFacturaPedido1.add(lf2);
		facturaPedido1.setLineasFacturas(lineasFacturaPedido1);
		facturaPedido1.setImporte(lf1.getPrecio() + lf2.getPrecio());
		facturaPedido1 = facturaEmitidaService.save(facturaPedido1);
		
		List<LineaFactura> lineasFacturaPedido2 = new ArrayList<LineaFactura>();
		
		LineaFactura lf3 = new LineaFactura();
		lf3.setFactura(facturaPedido2);
		lf3.setProducto(producto3);
		lf3.setCantidad(10);
		lf3.setPrecio(producto3.getPrecioVenta()*lf3.getCantidad());
		
		lf3 = lineaFacturaService.save(lf3);
		
		LineaFactura lf4 = new LineaFactura();
		lf4.setFactura(facturaPedido2);
		lf4.setProducto(producto2);
		lf4.setCantidad(10);
		lf4.setPrecio(producto2.getPrecioVenta()*lf4.getCantidad());
		
		lf4 = lineaFacturaService.save(lf4);
		
		lineasFacturaPedido2.add(lf3);
		lineasFacturaPedido2.add(lf4);
		facturaPedido2.setLineasFacturas(lineasFacturaPedido2);
		facturaPedido2.setImporte(lf3.getPrecio() + lf4.getPrecio());
		facturaPedido2 = facturaEmitidaService.save(facturaPedido2);
		
		List<LineaFactura> lineasFacturaPedido3 = new ArrayList<LineaFactura>();
		
		LineaFactura lf5 = new LineaFactura();
		lf5.setFactura(facturaPedido3);
		lf5.setProducto(producto3);
		lf5.setCantidad(10);
		lf5.setPrecio(producto3.getPrecioVenta()*lf5.getCantidad());
		
		lf5 = lineaFacturaService.save(lf5);
		
		LineaFactura lf6 = new LineaFactura();
		lf6.setFactura(facturaPedido3);
		lf6.setProducto(producto1);
		lf6.setCantidad(10);
		lf6.setPrecio(producto1.getPrecioVenta()*lf6.getCantidad());
		
		lf6 = lineaFacturaService.save(lf6);
		
		lineasFacturaPedido3.add(lf5);
		lineasFacturaPedido3.add(lf6);
		facturaPedido3.setLineasFacturas(lineasFacturaPedido3);
		facturaPedido3.setImporte(lf5.getPrecio() + lf6.getPrecio());
		facturaPedido3 = facturaEmitidaService.save(facturaPedido3);
		
		// CREACION DE PEDIDO
		
		Pedido pedido1 = new Pedido();
		pedido1.setDireccionEnvio("C/ Ejemplo");
		pedido1.setEstado(EstadoPedido.EN_ESPERA);
		pedido1.setFacturaEmitida(facturaPedido1);
		pedido1.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedido1.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	
		pedido1 = pedidoService.save(pedido1);
		
		Pedido pedido2 = new Pedido();
		pedido2.setDireccionEnvio("C/ Ejemplo");
		pedido2.setEstado(EstadoPedido.EN_ESPERA);
		pedido2.setFacturaEmitida(facturaPedido2);
		pedido2.setFechaEnvio(LocalDate.parse("22/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedido2.setFechaRealizacion(LocalDate.parse("22/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	
		pedido2 = pedidoService.save(pedido2);
		
		Pedido pedido3 = new Pedido();
		pedido3.setDireccionEnvio("C/ Ejemplo");
		pedido3.setEstado(EstadoPedido.EN_ESPERA);
		pedido3.setFacturaEmitida(facturaPedido3);
		pedido3.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedido3.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		pedido3 = pedidoService.save(pedido3);
		
		pedido3.setTransportista(transportista);
		pedido3.setEstado(EstadoPedido.PREPARADO);
		pedido3 = pedidoService.save(pedido3);


		// CREACION DE VEHICULO

		Vehiculo vehiculoDisponible = new Vehiculo();
		vehiculoDisponible.setMatricula("4040GND");
		vehiculoDisponible.setImagen("doc/images/camionpluma.png");
		vehiculoDisponible.setCapacidad(10000.);
		vehiculoDisponible.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculoDisponible.setMMA(10000.);
		
		vehiculoDisponible = vehiculoService.save(vehiculoDisponible);
		
		
		Vehiculo vehiculoNoDisponible = new Vehiculo();
		vehiculoNoDisponible.setMatricula("0000BBB");
		vehiculoNoDisponible.setImagen("doc/images/camionpluma.png");
		vehiculoNoDisponible.setCapacidad(100.);
		vehiculoNoDisponible.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculoNoDisponible.setMMA(450.);
		
		vehiculoNoDisponible = vehiculoService.save(vehiculoNoDisponible);
		
		// CREACION DE ITV
		
		FacturaRecibida facturaRecibidaITV = new FacturaRecibida();
		facturaRecibidaITV.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITV.setEstaPagada(true);
		facturaRecibidaITV.setImporte(50.);
		facturaRecibidaITV.setFechaEmision(LocalDate.of(2019, 10, 21));
		facturaRecibidaITV = facturaRecibidaService.save(facturaRecibidaITV);
		
		ITV itv = new ITV();
		itv.setVehiculo(vehiculoDisponible);
		itv.setFecha(LocalDate.of(2019, 10, 21));
		itv.setExpiracion(LocalDate.of(2022, 10, 21));
		itv.setRecibidas(facturaRecibidaITV);
		itv.setResultado(ResultadoITV.FAVORABLE);
		itv = itvService.save(itv);

		// CREACION DE SEGURO
		
		FacturaRecibida facturaRecibidaSeguro = new FacturaRecibida();
		facturaRecibidaSeguro.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguro.setEstaPagada(true);
		facturaRecibidaSeguro.setImporte(220.);
		facturaRecibidaSeguro.setFechaEmision(LocalDate.of(2019, 05, 21));
		facturaRecibidaSeguro =facturaRecibidaService.save(facturaRecibidaSeguro);

		Seguro seguro = new Seguro();
		seguro.setCompania("Linea Directa");
		seguro.setFechaContrato(LocalDate.of(2018, 05, 21));
		seguro.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguro.setRecibidas(facturaRecibidaSeguro);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculoDisponible);
		seguro = seguroService.save(seguro);	
		
		pedido3.setEstado(EstadoPedido.EN_REPARTO);
		pedido3.setVehiculo(vehiculoDisponible);
		
		pedido3 = pedidoService.save(pedido3);
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	* 										FUNCIONES DE LOS TESTS													 *
	* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */	
	@Test
	@Transactional
	@DisplayName("Busca todos los pedidos asociados a un vehiculo -- Caso Positivo")
	void findAllByVehiculo() {
		Vehiculo VehiculoMatricula4040GND = vehiculoService.findByMatricula("4040GND");
		List<Pedido> pedidosConVehiculoMatricula4040GND = pedidoService.findAllByVehiculo(VehiculoMatricula4040GND.getId());
		assertThat(pedidosConVehiculoMatricula4040GND.size()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca todos los pedidos asociados a un vehiculo -- Caso Negativo")
	void findAllByVehiculoNotFound() {
		Vehiculo VehiculoMatricula0000BBB = vehiculoService.findByMatricula("0000BBB");
		List<Pedido> pedidosConVehiculoMatricula0000BBB = pedidoService.findAllByVehiculo(VehiculoMatricula0000BBB.getId());
		assertThat(pedidosConVehiculoMatricula0000BBB.size()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca todos los pedidos realizados en una determinada fecha -- Caso Positivo")
	void findPedidosByFecha() {
		List<Pedido> pedidosConFecha = pedidoService.findPedidosByFecha(LocalDate.of(2020, 12, 20));
		assertThat(pedidosConFecha.size()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca todos los pedidos realizados en una determinada fecha -- Caso Negativo")
	void findPedidosByFechaNotFound() {
		List<Pedido> pedidosConFecha = pedidoService.findPedidosByFecha(LocalDate.of(2020, 12, 18));
		assertThat(pedidosConFecha.size()).isEqualTo(0);
	}

	@Test
	@Transactional
	@DisplayName("Busca todos los pedidos en reparto de una determinada fecha -- Caso Positivo")
	void findPedidosEnRepartoByFecha() {
		List<Pedido> pedidosConFechaEnReparto = pedidoService.findPedidosEnRepartoByFecha(LocalDate.of(2020, 12, 20));
		assertThat(pedidosConFechaEnReparto.size()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca todos los pedidos en reparto de una determinada fecha -- Caso Negativo")
	void findPedidosEnRepartoByFechaNotFound() {
		List<Pedido> pedidosConFechaEnReparto = pedidoService.findPedidosEnRepartoByFecha(LocalDate.of(2020, 12, 18));
		assertThat(pedidosConFechaEnReparto.size()).isEqualTo(0);
	}

	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	@Test
	@Transactional
	@DisplayName("Caso Positivo de añadir un pedido")
	void SavePedido() {
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest = pedidoService.save(pedidoTest);
		
		assertThat(pedidoService.findByID(pedidoTest.getId())).isEqualTo(pedidoTest);
	}
	
	//PedidoSinTransportistaException
	@Test
	@Transactional
	@DisplayName("RN: El pedido no puede tener asignado un vehiculo y no un transportista (NEW) -- Caso Negativo")
	void AddPedidoConVehiculoSinTransportistaException() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		Vehiculo vehiculoDisponible = vehiculoService.findByMatricula("4040GND");
		pedidoTest.setVehiculo(vehiculoDisponible);
		
		assertThrows(PedidoConVehiculoSinTransportistaException.class, ()->{pedidoService.save(pedidoTest);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: El pedido no puede tener asignado un vehiculo y no un transportista (Update) -- Caso Negativo")
	void UpdatePedidoConVehiculoSinTransportistaException() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		Pedido pedidoTest2 = pedidoService.save(pedidoTest);
		
		Vehiculo vehiculoDisponible = vehiculoService.findByMatricula("4040GND");
		pedidoTest2.setVehiculo(vehiculoDisponible);
		
		assertThrows(PedidoConVehiculoSinTransportistaException.class, ()->{pedidoService.save(pedidoTest2);});
	}
	
	//VehiculoNotAvailableException
	
	@Test
	@Transactional
	@DisplayName("RN: El pedido no puede tener asignado un vehiculo no disponible(NEW) -- Caso Negativo")
	void AddVehiculoNotAvailableException() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		Vehiculo vehiculoNoDisponible = vehiculoService.findByMatricula("0000BBB");
		pedidoTest.setVehiculo(vehiculoNoDisponible);
		pedidoTest.setTransportista(transportistaService.findAll().iterator().next());
		
		assertThrows(VehiculoNotAvailableException.class, ()->{pedidoService.save(pedidoTest);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: El pedido no puede tener asignado un vehiculo no disponible(Update) -- Caso Negativo")
	void UpdateVehiculoNotAvailableException() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		Pedido pedidoTest2 = pedidoService.save(pedidoTest);
		
		pedidoTest2.setTransportista(transportistaService.findAll().iterator().next());
		Vehiculo vehiculoNoDisponible = vehiculoService.findByMatricula("0000BBB");
		pedidoTest2.setVehiculo(vehiculoNoDisponible);
		assertThrows(VehiculoNotAvailableException.class, ()->{pedidoService.save(pedidoTest2);});
	}
	
	//MMAExceededException
	
	@Test
	@Transactional
	@DisplayName("RN: Los pedidos de un vehiculo no puede superar la MMA de este (NEW) -- Caso Negativo ")
	void AddPedidoMMAExceededException() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		Pedido pedidoTestBD = pedidoService.save(pedidoTest);

		Vehiculo vehiculoDisponibleTest = new Vehiculo();
		vehiculoDisponibleTest.setMatricula("2000BRD");
		vehiculoDisponibleTest.setImagen("doc/images/camionpluma.png");
		vehiculoDisponibleTest.setCapacidad(10000.);
		vehiculoDisponibleTest.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculoDisponibleTest.setMMA(500.);
		
		vehiculoDisponibleTest = vehiculoService.save(vehiculoDisponibleTest);
		
		FacturaRecibida facturaRecibidaITVTest = new FacturaRecibida();
		facturaRecibidaITVTest.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITVTest.setEstaPagada(true);
		facturaRecibidaITVTest.setImporte(50.);
		facturaRecibidaITVTest.setFechaEmision(LocalDate.of(2019, 10, 21));
		facturaRecibidaITVTest = facturaRecibidaService.save(facturaRecibidaITVTest);
		
		ITV itvTest = new ITV();
		itvTest.setVehiculo(vehiculoDisponibleTest);
		itvTest.setFecha(LocalDate.of(2019, 10, 21));
		itvTest.setExpiracion(LocalDate.of(2022, 10, 21));
		itvTest.setRecibidas(facturaRecibidaITVTest);
		itvTest.setResultado(ResultadoITV.FAVORABLE);
		itvTest = itvService.save(itvTest);
		
		FacturaRecibida facturaRecibidaSeguroTest = new FacturaRecibida();
		facturaRecibidaSeguroTest.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguroTest.setEstaPagada(true);
		facturaRecibidaSeguroTest.setImporte(220.);
		facturaRecibidaSeguroTest.setFechaEmision(LocalDate.of(2019, 05, 21));
		facturaRecibidaSeguroTest =facturaRecibidaService.save(facturaRecibidaSeguroTest);

		Seguro seguroTest = new Seguro();
		seguroTest.setCompania("Linea Directa");
		seguroTest.setFechaContrato(LocalDate.of(2018, 05, 21));
		seguroTest.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguroTest.setRecibidas(facturaRecibidaSeguroTest);
		seguroTest.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguroTest.setVehiculo(vehiculoDisponibleTest);
		seguroTest = seguroService.save(seguroTest);
		
		pedidoTestBD.setEstado(EstadoPedido.EN_REPARTO);
		pedidoTestBD.setTransportista(transportistaService.findAll().iterator().next());
		pedidoTestBD.setVehiculo(vehiculoDisponibleTest);
		
		Producto productoPedido = pedidoTestBD.getFacturaEmitida().getLineasFacturas().get(0).getProducto();
		assertThat(pedidoTestBD.getFacturaEmitida().getLineasFacturas().get(0).getCantidad()*productoPedido.getPesoUnitario()).isGreaterThan(vehiculoDisponibleTest.getMMA());
		assertThrows(MMAExceededException.class, ()->{pedidoService.save(pedidoTestBD);});
		
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Los pedidos de un vehiculo no puede superar la MMA de este (Update) -- Caso Negativo ")
	void UpdatePedidoMMAExceededException() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(1);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		Pedido pedidoTestBD = pedidoService.save(pedidoTest);

		Vehiculo vehiculoDisponibleTest = new Vehiculo();
		vehiculoDisponibleTest.setMatricula("2000BRD");
		vehiculoDisponibleTest.setImagen("doc/images/camionpluma.png");
		vehiculoDisponibleTest.setCapacidad(10000.);
		vehiculoDisponibleTest.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculoDisponibleTest.setMMA(500.);
		
		vehiculoDisponibleTest = vehiculoService.save(vehiculoDisponibleTest);
		
		FacturaRecibida facturaRecibidaITVTest = new FacturaRecibida();
		facturaRecibidaITVTest.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITVTest.setEstaPagada(true);
		facturaRecibidaITVTest.setImporte(50.);
		facturaRecibidaITVTest.setFechaEmision(LocalDate.of(2019, 10, 21));
		facturaRecibidaITVTest = facturaRecibidaService.save(facturaRecibidaITVTest);
		
		ITV itvTest = new ITV();
		itvTest.setVehiculo(vehiculoDisponibleTest);
		itvTest.setFecha(LocalDate.of(2019, 10, 21));
		itvTest.setExpiracion(LocalDate.of(2022, 10, 21));
		itvTest.setRecibidas(facturaRecibidaITVTest);
		itvTest.setResultado(ResultadoITV.FAVORABLE);
		itvTest = itvService.save(itvTest);
		
		FacturaRecibida facturaRecibidaSeguroTest = new FacturaRecibida();
		facturaRecibidaSeguroTest.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguroTest.setEstaPagada(true);
		facturaRecibidaSeguroTest.setImporte(220.);
		facturaRecibidaSeguroTest.setFechaEmision(LocalDate.of(2019, 05, 21));
		facturaRecibidaSeguroTest =facturaRecibidaService.save(facturaRecibidaSeguroTest);

		Seguro seguroTest = new Seguro();
		seguroTest.setCompania("Linea Directa");
		seguroTest.setFechaContrato(LocalDate.of(2018, 05, 21));
		seguroTest.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguroTest.setRecibidas(facturaRecibidaSeguroTest);
		seguroTest.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguroTest.setVehiculo(vehiculoDisponibleTest);
		seguroTest = seguroService.save(seguroTest);
		
		pedidoTestBD.setEstado(EstadoPedido.EN_REPARTO);
		pedidoTestBD.setTransportista(transportistaService.findAll().iterator().next());
		pedidoTestBD.setVehiculo(vehiculoDisponibleTest);
		Producto productoPedido = pedidoTestBD.getFacturaEmitida().getLineasFacturas().get(0).getProducto();
		assertThat(pedidoTestBD.getFacturaEmitida().getLineasFacturas().get(0).getCantidad()*productoPedido.getPesoUnitario()).isLessThan(vehiculoDisponibleTest.getMMA());
		Pedido pedidoTestBDUpdated = pedidoService.save(pedidoTestBD);
		
		//UPDATE NUMERO DE PRODUCTOS EN EL PEDIDO
		LineaFactura lftestUpdate = new LineaFactura();
		lftestUpdate.setFactura(facturaPedidoTest);
		lftestUpdate.setProducto(productoService.findAll().get(0));
		lftestUpdate.setCantidad(10);
		lftestUpdate.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftestUpdate);

				
		productoPedido = pedidoService.findByID(pedidoTestBDUpdated.getId()).getFacturaEmitida().getLineasFacturas().get(0).getProducto();
		assertThat(pedidoService.findByID(pedidoTestBDUpdated.getId()).getFacturaEmitida().getLineasFacturas().get(0).getCantidad()*productoPedido.getPesoUnitario()).isGreaterThan(vehiculoDisponibleTest.getMMA());
		assertThrows(MMAExceededException.class, ()->{pedidoService.save(pedidoTestBD);});
		
	}
	
	//VehiculoDimensionesExceededException
	
	@Test
	@Transactional
	@DisplayName("RN: Los pedidos de un vehiculo no puede superar las dimensiones de este (NEW) -- Caso Negativo ")
	void AddVehiculoDimensionesExceededException() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(11);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		Pedido pedidoTestBD = pedidoService.save(pedidoTest);

		Vehiculo vehiculoDisponibleTest = new Vehiculo();
		vehiculoDisponibleTest.setMatricula("2000BRD");
		vehiculoDisponibleTest.setImagen("doc/images/camionpluma.png");
		vehiculoDisponibleTest.setCapacidad(10.);
		vehiculoDisponibleTest.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculoDisponibleTest.setMMA(10000.);
		
		vehiculoDisponibleTest = vehiculoService.save(vehiculoDisponibleTest);
		
		FacturaRecibida facturaRecibidaITVTest = new FacturaRecibida();
		facturaRecibidaITVTest.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITVTest.setEstaPagada(true);
		facturaRecibidaITVTest.setImporte(50.);
		facturaRecibidaITVTest.setFechaEmision(LocalDate.of(2019, 10, 21));
		facturaRecibidaITVTest = facturaRecibidaService.save(facturaRecibidaITVTest);
		
		ITV itvTest = new ITV();
		itvTest.setVehiculo(vehiculoDisponibleTest);
		itvTest.setFecha(LocalDate.of(2019, 10, 21));
		itvTest.setExpiracion(LocalDate.of(2022, 10, 21));
		itvTest.setRecibidas(facturaRecibidaITVTest);
		itvTest.setResultado(ResultadoITV.FAVORABLE);
		itvTest = itvService.save(itvTest);
		
		FacturaRecibida facturaRecibidaSeguroTest = new FacturaRecibida();
		facturaRecibidaSeguroTest.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguroTest.setEstaPagada(true);
		facturaRecibidaSeguroTest.setImporte(220.);
		facturaRecibidaSeguroTest.setFechaEmision(LocalDate.of(2019, 05, 21));
		facturaRecibidaSeguroTest =facturaRecibidaService.save(facturaRecibidaSeguroTest);

		Seguro seguroTest = new Seguro();
		seguroTest.setCompania("Linea Directa");
		seguroTest.setFechaContrato(LocalDate.of(2018, 05, 21));
		seguroTest.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguroTest.setRecibidas(facturaRecibidaSeguroTest);
		seguroTest.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguroTest.setVehiculo(vehiculoDisponibleTest);
		seguroTest = seguroService.save(seguroTest);
		
		pedidoTestBD.setEstado(EstadoPedido.EN_REPARTO);
		pedidoTestBD.setTransportista(transportistaService.findAll().iterator().next());
		pedidoTestBD.setVehiculo(vehiculoDisponibleTest);
		
		Producto productoPedido = pedidoTestBD.getFacturaEmitida().getLineasFacturas().get(0).getProducto();
		Double dimensionesPedido = productoPedido.getAlto()*productoPedido.getAncho()*productoPedido.getProfundo();
		assertThat(pedidoTestBD.getFacturaEmitida().getLineasFacturas().get(0).getCantidad()*dimensionesPedido).isGreaterThan(vehiculoDisponibleTest.getCapacidad());
		assertThrows(VehiculoDimensionesExceededException.class, ()->{pedidoService.save(pedidoTestBD);});
		
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Los pedidos de un vehiculo no puede superar las dimensiones de este (Update) -- Caso Negativo ")
	void UpdateVehiculoDimensionesExceededException() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(1);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		Pedido pedidoTestBD = pedidoService.save(pedidoTest);

		Vehiculo vehiculoDisponibleTest = new Vehiculo();
		vehiculoDisponibleTest.setMatricula("2000BRD");
		vehiculoDisponibleTest.setImagen("doc/images/camionpluma.png");
		vehiculoDisponibleTest.setCapacidad(10.);
		vehiculoDisponibleTest.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculoDisponibleTest.setMMA(100000.);
		
		vehiculoDisponibleTest = vehiculoService.save(vehiculoDisponibleTest);
		
		FacturaRecibida facturaRecibidaITVTest = new FacturaRecibida();
		facturaRecibidaITVTest.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITVTest.setEstaPagada(true);
		facturaRecibidaITVTest.setImporte(50.);
		facturaRecibidaITVTest.setFechaEmision(LocalDate.of(2019, 10, 21));
		facturaRecibidaITVTest = facturaRecibidaService.save(facturaRecibidaITVTest);
		
		ITV itvTest = new ITV();
		itvTest.setVehiculo(vehiculoDisponibleTest);
		itvTest.setFecha(LocalDate.of(2019, 10, 21));
		itvTest.setExpiracion(LocalDate.of(2022, 10, 21));
		itvTest.setRecibidas(facturaRecibidaITVTest);
		itvTest.setResultado(ResultadoITV.FAVORABLE);
		itvTest = itvService.save(itvTest);
		
		FacturaRecibida facturaRecibidaSeguroTest = new FacturaRecibida();
		facturaRecibidaSeguroTest.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguroTest.setEstaPagada(true);
		facturaRecibidaSeguroTest.setImporte(220.);
		facturaRecibidaSeguroTest.setFechaEmision(LocalDate.of(2019, 05, 21));
		facturaRecibidaSeguroTest =facturaRecibidaService.save(facturaRecibidaSeguroTest);

		Seguro seguroTest = new Seguro();
		seguroTest.setCompania("Linea Directa");
		seguroTest.setFechaContrato(LocalDate.of(2018, 05, 21));
		seguroTest.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguroTest.setRecibidas(facturaRecibidaSeguroTest);
		seguroTest.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguroTest.setVehiculo(vehiculoDisponibleTest);
		seguroTest = seguroService.save(seguroTest);
		
		pedidoTestBD.setEstado(EstadoPedido.EN_REPARTO);
		pedidoTestBD.setTransportista(transportistaService.findAll().iterator().next());
		pedidoTestBD.setVehiculo(vehiculoDisponibleTest);
		Producto productoPedido = pedidoTestBD.getFacturaEmitida().getLineasFacturas().get(0).getProducto();
		Double dimensionesPedido = productoPedido.getAlto()*productoPedido.getAncho()*productoPedido.getProfundo();
		assertThat(pedidoTestBD.getFacturaEmitida().getLineasFacturas().get(0).getCantidad()*dimensionesPedido).isLessThan(vehiculoDisponibleTest.getCapacidad());
		Pedido pedidoTestBDUpdated = pedidoService.save(pedidoTestBD);
		
		//UPDATE NUMERO DE PRODUCTOS EN EL PEDIDO
		
		LineaFactura lftestUpdate = new LineaFactura();
		lftestUpdate.setFactura(facturaPedidoTest);
		lftestUpdate.setProducto(productoService.findAll().get(0));
		lftestUpdate.setCantidad(10);
		lftestUpdate.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftestUpdate);
				
				
		productoPedido = pedidoService.findByID(pedidoTestBDUpdated.getId()).getFacturaEmitida().getLineasFacturas().get(0).getProducto();
		
		assertThat(pedidoService.findByID(pedidoTestBDUpdated.getId()).getFacturaEmitida().getLineasFacturas().get(0).getCantidad()*dimensionesPedido).isGreaterThan(vehiculoDisponibleTest.getCapacidad());
		assertThrows(VehiculoDimensionesExceededException.class, ()->{pedidoService.save(pedidoTestBD);});
		
	}
	
	//PedidoLogisticException
	
	@Test
	@Transactional
	@DisplayName("RN: Un pedido con vehiculo debe de estar en reparto o entregado (NEW) -- Caso Negativo")
	void AddPedidoLogisticExceptionConVehiculoYEstadoNotRepartoOEntregado() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		Vehiculo vehiculoDisponible = vehiculoService.findByMatricula("4040GND");
		pedidoTest.setVehiculo(vehiculoDisponible);
		pedidoTest.setTransportista(transportistaService.findAll().iterator().next());
		
		assertThrows(PedidoLogisticException.class, ()->{pedidoService.save(pedidoTest);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Un pedido con vehiculo debe de estar en reparto o entregado (Update) -- Caso Negativo")
	void UpdatePedidoLogisticExceptionConVehiculoYEstadoNotRepartoOEntregado() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		Pedido pedidoTestBD = pedidoService.save(pedidoTest);
		Vehiculo vehiculoDisponible = vehiculoService.findByMatricula("4040GND");
		pedidoTestBD.setVehiculo(vehiculoDisponible);
		pedidoTestBD.setTransportista(transportistaService.findAll().iterator().next());
		
		assertThrows(PedidoLogisticException.class, ()->{pedidoService.save(pedidoTestBD);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Un pedido con transportista no puede estar en espera (NEW) -- Caso Negativo")
	void AddPedidoLogisticExceptionConTransportistaYEstadoEnEspera() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setTransportista(transportistaService.findAll().iterator().next());
		
		assertThrows(PedidoLogisticException.class, ()->{pedidoService.save(pedidoTest);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Un pedido con transportista no puede estar en espera (Update) -- Caso Negativo")
	void UpdatePedidoLogisticExceptionConTransportistaYEstadoEnEspera() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		Pedido pedidoTestBD = pedidoService.save(pedidoTest);
		pedidoTestBD.setTransportista(transportistaService.findAll().iterator().next());
		
		assertThrows(PedidoLogisticException.class, ()->{pedidoService.save(pedidoTestBD);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Un pedido sin transportista tiene que estar en espera (NEW) -- Caso Negativo")
	void AddPedidoLogisticExceptionSinTransportistaYEstadoNotEnEspera() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.PREPARADO);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		assertThrows(PedidoLogisticException.class, ()->{pedidoService.save(pedidoTest);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Un pedido sin transportista tiene que estar en espera (Update) -- Caso Negativo")
	void UpdatePedidoLogisticExceptionSinTransportistaYEstadoNotEnEspera() {
		
		FacturaEmitida facturaPedidoTest = new FacturaEmitida();
		facturaPedidoTest.setCliente(clienteService.findAll().iterator().next());
		facturaPedidoTest.setDependiente(dependienteService.findAll().iterator().next());
		facturaPedidoTest.setEstaPagada(true);
		facturaPedidoTest.setFechaEmision(LocalDate.parse("17/09/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		facturaPedidoTest.setImporte(0.);
		
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		List<LineaFactura> lineasFacturaPedidoTest = new ArrayList<LineaFactura>();
		
		LineaFactura lftest = new LineaFactura();
		lftest.setFactura(facturaPedidoTest);
		lftest.setProducto(productoService.findAll().get(0));
		lftest.setCantidad(10);
		lftest.setPrecio(lftest.getProducto().getPrecioVenta()*lftest.getCantidad());
		
		lftest = lineaFacturaService.save(lftest);
		
		lineasFacturaPedidoTest.add(lftest);
		facturaPedidoTest.setLineasFacturas(lineasFacturaPedidoTest);
		facturaPedidoTest.setImporte(lftest.getPrecio());
		facturaPedidoTest = facturaEmitidaService.save(facturaPedidoTest);
		
		Pedido pedidoTest = new Pedido();
		pedidoTest.setDireccionEnvio("C/ Ejemplo");
		pedidoTest.setEstado(EstadoPedido.EN_ESPERA);
		pedidoTest.setFacturaEmitida(facturaPedidoTest);
		pedidoTest.setFechaEnvio(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pedidoTest.setFechaRealizacion(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		Pedido pedidoTestBD = pedidoService.save(pedidoTest);
		pedidoTestBD.setEstado(EstadoPedido.PREPARADO);
		
		assertThrows(PedidoLogisticException.class, ()->{pedidoService.save(pedidoTestBD);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Un pedido entregado no puede modificarse -- Caso Negativo")
	void UpdatePedidoEntregado() {
		Pedido pedidoEntregado = pedidoService.findPedidosEnRepartoByFecha(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0);
		pedidoEntregado.setEstado(EstadoPedido.ENTREGADO);
		pedidoService.save(pedidoEntregado);
		
		//intento actualizar un pedido entregado
		pedidoEntregado.setDireccionEnvio("C/ cambio de direccion ilegalisimo");
		
		assertThrows(UnmodifablePedidoException.class, () -> pedidoService.save(pedidoEntregado));
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Un pedido cancelado no puede modificarse -- Caso Negativo")
	void UpdatePedidoCancelado() {
		Pedido pedidoCancelado = pedidoService.findAll().iterator().next();
		pedidoCancelado.setEstado(EstadoPedido.CANCELADO);
		pedidoService.save(pedidoCancelado);
		
		//intento actualizar un pedido entregado
		pedidoCancelado.setEstado(EstadoPedido.EN_TIENDA);
		
		assertThrows(UnmodifablePedidoException.class, () -> pedidoService.save(pedidoCancelado));
	}
	
	@Test
	@Transactional
	@DisplayName("RN: No se puede cancelar un pedido enviado -- Caso Negativo")
	void UpdateCancelaPedidoEnviado() {
		Pedido pedidoCancelado = pedidoService.findPedidosEnRepartoByFecha(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0);
		pedidoCancelado.setEstado(EstadoPedido.CANCELADO);
				
		assertThrows(UnmodifablePedidoException.class, () -> pedidoService.save(pedidoCancelado));
	}
	
	@Test
	@Transactional
	@DisplayName("RN: No se puede modificar un pedido enviado -- Caso Negativo")
	void UpdatePedidoEnviado() {
		Pedido pedidoEnviado = pedidoService.findPedidosEnRepartoByFecha(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0);
		pedidoEnviado.setDireccionEnvio("Direccion ilegalisima");
				
		assertThrows(UnmodifablePedidoException.class, () -> pedidoService.save(pedidoEnviado));
	}
	
	/* * * * * * * * * * * * *
	 *  FUNCIONALIDAD TEST   *
	 * * * * * * * * * * * * */
	@Test
	@Transactional
	@DisplayName("Añadir factura rectificada ya enviado- caso positivo")
	void PedidoYaEnviadoRectificacion() {
		Pedido pedidoEnviado = pedidoService.findPedidosEnRepartoByFecha(LocalDate.parse("20/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy"))).get(0);
		Producto producto1 = pedidoEnviado.getFacturaEmitida().getLineasFacturas().get(0).getProducto();
		Producto producto2 = pedidoEnviado.getFacturaEmitida().getLineasFacturas().get(1).getProducto();
		FacturaEmitida factOriginal = pedidoEnviado.getFacturaEmitida();
		
		FacturaEmitida facturaPedido3Rect = new FacturaEmitida();
		facturaPedido3Rect.setCliente(factOriginal.getCliente());
		facturaPedido3Rect.setDependiente(factOriginal.getDependiente());
		facturaPedido3Rect.setEstaPagada(true);
		facturaPedido3Rect.setImporte(3100.15);
		
		facturaPedido3Rect.setOriginal(pedidoEnviado.getFacturaEmitida());
		facturaPedido3Rect = facturaEmitidaService.save(facturaPedido3Rect);
		factOriginal.setRectificativa(facturaPedido3Rect);
		
		factOriginal = facturaEmitidaService.save(factOriginal);
		
		List<LineaFactura> lineasFacturaPedido3 = new ArrayList<LineaFactura>();
		
		LineaFactura lf1 = new LineaFactura();
		lf1.setFactura(facturaPedido3Rect);
		lf1.setProducto(producto1);
		lf1.setCantidad(10);
		lf1.setPrecio(producto1.getPrecioVenta()*lf1.getCantidad());
		
		lf1 = lineaFacturaService.save(lf1);
		
		LineaFactura lf2 = new LineaFactura();
		lf2.setFactura(facturaPedido3Rect);
		lf2.setProducto(producto2);
		lf2.setCantidad(10);
		lf2.setPrecio(producto2.getPrecioVenta()*lf2.getCantidad());
		
		lf2 = lineaFacturaService.save(lf2);
		
		lineasFacturaPedido3.add(lf1);
		lineasFacturaPedido3.add(lf2);

		facturaPedido3Rect.setLineasFacturas(lineasFacturaPedido3);
		facturaPedido3Rect = facturaEmitidaService.save(facturaPedido3Rect);
		
		
		assertThat(pedidoEnviado.getFacturaEmitida()).isEqualTo(facturaPedido3Rect);
		assertThat(pedidoEnviado.getFacturaEmitida().getNumFactura()).isEqualTo("RCTE-1");
		assertThat(pedidoEnviado.getFacturaEmitida().getFechaEmision()).isEqualTo(factOriginal.getFechaEmision());
	}
}