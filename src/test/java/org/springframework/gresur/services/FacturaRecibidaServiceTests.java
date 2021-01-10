package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
import org.springframework.gresur.model.Concepto;
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoSeguro;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Unidad;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
public class FacturaRecibidaServiceTests {

	@Autowired
	protected ProveedorService proveedorService;
	
	@Autowired
	protected FacturaRecibidaService facturaRecibidaService;
	
	@Autowired
	protected ProductoService productoService;
	
	@Autowired
	protected EstanteriaService estanteriaService;
	
	@Autowired
	protected AlmacenService almacenService;

	@Autowired
	protected LineasFacturaService lineaFacturaService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ITVService itvService;
	
	@Autowired
	protected SeguroService seguroService;
	
	@Autowired
	protected ReparacionService reparacionService;
	
	@Autowired
	protected ConfiguracionService confService;
	
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
	void initAll() {
		
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
		almacen.setCapacidad(2000.);
		almacen.setDireccion("Carretera Las Columnas Km92");
		almacenService.save(almacen);
		
		// CREACION DE ESTANTERIA
		Estanteria estanteriaAzulejos = new Estanteria();
		estanteriaAzulejos.setCategoria(Categoria.AZULEJOS);
		estanteriaAzulejos.setAlmacen(almacen);
		estanteriaAzulejos.setCapacidad(790.);
		estanteriaAzulejos = estanteriaService.save(estanteriaAzulejos);

		
		Producto azulejo = new Producto();
		azulejo.setAlto(0.2);
		azulejo.setAncho(0.2);
		azulejo.setProfundo(0.1);
		azulejo.setDescripcion("Azulejo de Decoracion Pared de Cocina");
		azulejo.setNombre("Azulejo UwYugsi");
		azulejo.setPesoUnitario(0.1);
		azulejo.setPrecioCompra(12.);
		azulejo.setPrecioVenta(15.);
		azulejo.setStock(5);
		azulejo.setStockSeguridad(3);
		azulejo.setUnidad(Unidad.UNIDADES);
		azulejo.setURLImagen("docs/imgs/azulejococina.png");
		azulejo.setEstanteria(estanteriaAzulejos);
		azulejo = productoService.save(azulejo);
		
		Producto azulejoSuelo = new Producto();
		azulejoSuelo.setAlto(0.2);
		azulejoSuelo.setAncho(0.2);
		azulejoSuelo.setProfundo(0.1);
		azulejoSuelo.setDescripcion("Azulejo de imitacion de madera para el suelo");
		azulejoSuelo.setNombre("Azulejo ugsi ugsi Suelo");
		azulejoSuelo.setPesoUnitario(0.1);
		azulejoSuelo.setPrecioCompra(12.);
		azulejoSuelo.setPrecioVenta(15.);
		azulejoSuelo.setStock(5);
		azulejoSuelo.setStockSeguridad(3);
		azulejoSuelo.setUnidad(Unidad.UNIDADES);
		azulejoSuelo.setURLImagen("docs/imgs/azulejosuelo.png");
		azulejoSuelo.setEstanteria(estanteriaAzulejos);
		azulejoSuelo = productoService.save(azulejoSuelo);



		// CREACION DE PROVEEDOR
		
		Proveedor proveedor = new Proveedor();
		proveedor.setName("Azulejos Hermanos Sainz");
		proveedor.setDireccion("Calle Reina Leonor Nº17");
		proveedor.setEmail("hierrossainz@gmail.com");
		proveedor.setTlf("987582210");
		proveedor.setNIF("10030284R");
		proveedor.setIBAN("ES6621000418401234567891");
		proveedor = proveedorService.save(proveedor);
			
		// CREACION DE FACTURA RECIBIDA
		
		FacturaRecibida facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.REPOSICION_STOCK);
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setFechaEmision(LocalDate.now());
		facturaRecibida.setImporte(1750.);
		facturaRecibida.setProveedor(proveedor);
		facturaRecibida = facturaRecibidaService.save(facturaRecibida);
		
		
		List<LineaFactura> lineas = new ArrayList<LineaFactura>();	
		LineaFactura lnf1 = new LineaFactura();
		LineaFactura lnf2 = new LineaFactura();


		lnf1.setFactura(facturaRecibida);
		lnf1.setCantidad(120);
		lnf1.setPrecio(12.);
		lnf1.setProducto(azulejo);
		lnf1 = lineaFacturaService.save(lnf1);

		
		lnf2.setFactura(facturaRecibida);
		lnf2.setCantidad(50);
		lnf2.setPrecio(5.);
		lnf2.setProducto(azulejoSuelo);
		lnf2 = lineaFacturaService.save(lnf2);
		
		lineas.add(lnf1);
		lineas.add(lnf2);

		facturaRecibida.setLineasFacturas(lineas);
		facturaRecibidaService.save(facturaRecibida);
		
		
		//CREACION DE VEHICULO JUNTO A SU REPACION, SEGURO E ITV
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4040GND");
		vehiculo.setImagen("doc/images/camionpluma.png");
		vehiculo.setCapacidad(100.);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setMMA(450.);
		
		vehiculo = vehiculoService.save(vehiculo);
		
		
		//Reparacion de vehiculo
		
		FacturaRecibida facturaRecibidaReparacion = new FacturaRecibida();
		facturaRecibidaReparacion.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion.setEstaPagada(true);
		facturaRecibidaReparacion.setImporte(220.);
		facturaRecibidaReparacion.setFechaEmision(LocalDate.of(2019, 10, 20));
		facturaRecibidaService.save(facturaRecibidaReparacion);
		
		Reparacion reparacion = new Reparacion();
		reparacion.setFechaEntradaTaller(LocalDate.of(2019, 10, 19));
		reparacion.setFechaSalidaTaller(LocalDate.of(2019, 10, 20));
		reparacion.setRecibidas(facturaRecibidaReparacion);
		reparacion.setVehiculo(vehiculo);
		reparacionService.save(reparacion);		
		
		//ITV de vehiculo
		
		FacturaRecibida facturaRecibidaITV = new FacturaRecibida();
		facturaRecibidaITV.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITV.setEstaPagada(true);
		facturaRecibidaITV.setImporte(50.);
		facturaRecibidaITV.setFechaEmision(LocalDate.of(2019, 10, 21));
		facturaRecibidaService.save(facturaRecibidaITV);
		
		ITV itv = new ITV();
		itv.setVehiculo(vehiculo);
		itv.setFecha(LocalDate.of(2019, 10, 21));
		itv.setExpiracion(LocalDate.of(2022, 10, 21));
		itv.setRecibidas(facturaRecibidaITV);
		itv.setResultado(ResultadoITV.FAVORABLE);
		itvService.save(itv);
		
		//Seguro de vehiculo
		
		FacturaRecibida facturaRecibidaSeguro = new FacturaRecibida();
		facturaRecibidaSeguro.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguro.setEstaPagada(true);
		facturaRecibidaSeguro.setImporte(220.);
		facturaRecibidaSeguro.setFechaEmision(LocalDate.of(2019, 05, 21));
		facturaRecibidaService.save(facturaRecibidaSeguro);

		
		Seguro seguro = new Seguro();
		seguro.setCompania("Linea Directa");
		seguro.setFechaContrato(LocalDate.of(2018, 05, 21));
		seguro.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguro.setRecibidas(facturaRecibidaSeguro);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculo);
		seguroService.save(seguro);
		
				
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	* 										FUNCIONES DE LOS TESTS													 *
	* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Busca todas las lineas facturas asociadas a facturas recibidas")
	void findLineasFactura() {

		List<LineaFactura> lf = facturaRecibidaService.findLineasFactura();

		assertThat(lf.get(0).getPrecio()).isEqualTo(12.);
		assertThat(lf.get(1).getPrecio()).isEqualTo(5.);

	}
	
	
	/* * * * * * * * * * * * * *
	 *   DELETE CASCADE TESTS  *
	 * * * * * * * * * * * * * */

	@Test
	@Transactional
	@DisplayName("Borrar FacturaRecibida-ITV por su id -- Caso positivo")
	void deleteRecibidaByIdITV() {
		List<FacturaRecibida> lfr = facturaRecibidaService.findAll();
		FacturaRecibida fr = lfr.get(2);
		
		facturaRecibidaService.deleteById(fr.getId());
				
		assertThat(itvService.count()).isEqualTo(0);
		assertThat(reparacionService.count()).isEqualTo(1);
		assertThat(seguroService.count()).isEqualTo(1);
		
		assertThat(vehiculoService.getDisponibilidad("4040GND")).isFalse();
		
		assertThat(facturaRecibidaService.count()).isEqualTo(3);
	}
	
	@Test
	@Transactional
	@DisplayName("Borrar FacturaRecibida-Reparacion por su id -- Caso positivo")
	void deleteRecibidaByIdReparacion() {
		List<FacturaRecibida> lfr = facturaRecibidaService.findAll();
		FacturaRecibida fr = lfr.get(1);
		
		facturaRecibidaService.deleteById(fr.getId());
				
		assertThat(itvService.count()).isEqualTo(1);
		assertThat(reparacionService.count()).isEqualTo(0);
		assertThat(seguroService.count()).isEqualTo(1);
		
		assertThat(vehiculoService.getDisponibilidad("4040GND")).isTrue();
		
		assertThat(facturaRecibidaService.count()).isEqualTo(3);
	}
	
	@Test
	@Transactional
	@DisplayName("Borrar FacturaRecibida-Seguro por su id -- Caso positivo")
	void deleteRecibidaByIdSeguro() {
		List<FacturaRecibida> lfr = facturaRecibidaService.findAll();
		FacturaRecibida fr = lfr.get(3);
		
		facturaRecibidaService.deleteById(fr.getId());
				
		assertThat(itvService.count()).isEqualTo(1);
		assertThat(reparacionService.count()).isEqualTo(1);
		assertThat(seguroService.count()).isEqualTo(0);
		
		assertThat(vehiculoService.getDisponibilidad("4040GND")).isFalse();
		
		assertThat(facturaRecibidaService.count()).isEqualTo(3);
	}
}