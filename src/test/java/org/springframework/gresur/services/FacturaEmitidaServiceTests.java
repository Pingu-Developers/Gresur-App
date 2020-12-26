package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.*;
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
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.Unidad;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.ClienteService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.PedidoService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.exceptions.ClienteDefaulterException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
public class FacturaEmitidaServiceTests {
	
	@Autowired
	protected FacturaEmitidaService facturaEmitidaService;
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected DependienteService dependienteService;
	
	@Autowired
	protected LineasFacturaService lineaFacturaService;
	
	@Autowired
	protected ProductoService productoService;
	
	@Autowired
	protected PedidoService pedidoService;
	
	@Autowired
	protected EstanteriaService estanteriaService;
	
	@Autowired
	protected AlmacenService almacenService;
	
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
		
		
		Almacen almacen = new Almacen();
		almacen.setCapacidad(1500.00);
		almacen.setDireccion("C/ Almacen");
		
		almacenService.save(almacen);
		
		
		Estanteria estanteria = new Estanteria();
		estanteria.setAlmacen(almacen);
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
	}
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Busca todas las lineas facturas asociadas a facturas emitidas")
	void findLineasFactura() {
		List<LineaFactura> lf = facturaEmitidaService.findLineasFactura();
		assertThat(lf.get(0).getPrecio()).isEqualTo(100.15);
		assertThat(lf.get(1).getPrecio()).isEqualTo(220.00);
	}
	
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("RN: No se vende a clientes con impagos (new) -- caso positivo")
	void saveClienteDefaulterPositive() {
		
		FacturaEmitida fem = facturaEmitidaService.findAll().get(0);
		Cliente noDefaulter = fem.getCliente();
		
		FacturaEmitida nuevaCompra = new FacturaEmitida();
		nuevaCompra.setCliente(noDefaulter);
		nuevaCompra.setDependiente(dependienteService.findByNIF("12345678L"));
		nuevaCompra.setImporte(290.01);
		nuevaCompra.setFecha(LocalDate.of(2020, 12, 1));
		nuevaCompra.setEstaPagada(true);
		
		nuevaCompra = facturaEmitidaService.save(nuevaCompra);
		
		assertThat(facturaEmitidaService.findByNumFactura(nuevaCompra.getId())).isEqualTo(nuevaCompra);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: No se vende a clientes con impagos (update) -- caso positivo")
	void updateClienteDefaulterPositive() {
		
		/* pongo la factura en sin pagar (esto no da problema al hacerlo)*/
		FacturaEmitida fem = facturaEmitidaService.findAll().get(0);
		fem.setEstaPagada(false);		
		assertThat(facturaEmitidaService.findByNumFactura(fem.getId()).getEstaPagada()).isEqualTo(false);
		
		/* Para una factura sin pagar debe dejarme cambiar el atributo estaPagada, pero ningun otro*/
		fem.setEstaPagada(true);
		assertThat(facturaEmitidaService.findByNumFactura(fem.getId()).getEstaPagada()).isEqualTo(true);

	}
		
	@Test
	@Transactional
	@DisplayName("RN: No se vende a clientes con impagos (new) -- caso negativo")
	void saveClienteDefaulterNegative() {
		
		FacturaEmitida fem = facturaEmitidaService.findAll().get(0);
		fem.setEstaPagada(false);
		fem = facturaEmitidaService.save(fem);
		
		Cliente defaulter = fem.getCliente();
		
		FacturaEmitida nuevaCompra = new FacturaEmitida();
		nuevaCompra.setCliente(defaulter);
		nuevaCompra.setDependiente(dependienteService.findByNIF("12345678L"));
		nuevaCompra.setImporte(290.01);
		nuevaCompra.setFecha(LocalDate.of(2020, 12, 1));
		nuevaCompra.setEstaPagada(true);
		
		assertThrows(ClienteDefaulterException.class, () -> {facturaEmitidaService.save(nuevaCompra);});
		List<FacturaEmitida> lfem = facturaEmitidaService.findAll();
		assertThat(lfem.get(lfem.size()-1)).isNotEqualTo(nuevaCompra);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: No se vende a clientes con impagos (update) -- caso negativo")
	void updateClienteDefaulterNegative() {
		
		/* pongo la factura en sin pagar (esto no da problema al hacerlo)*/
		FacturaEmitida fem = facturaEmitidaService.findAll().get(0);
		fem.setEstaPagada(false);		
		FacturaEmitida femUpdate = facturaEmitidaService.save(fem);

		assertThat(facturaEmitidaService.findByNumFactura(fem.getId()).getEstaPagada()).isEqualTo(false);
		
		/* Para una factura sin pagar debe dejarme cambiar el atributo estaPagada, pero ningun otro*/
		fem.setImporte(928.13929);
		assertThrows(ClienteDefaulterException.class, ()->{facturaEmitidaService.save(femUpdate);});
	}
}
