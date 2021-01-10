package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.Concepto;
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoSeguro;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.ClienteService;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.PedidoService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.service.exceptions.MatriculaUnsupportedPatternException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class VehiculoServiceTests {
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ITVService itvService;
	
	@Autowired
	protected SeguroService seguroService;
	
	@Autowired
	protected ReparacionService reparacionService;
	
	@Autowired
	protected FacturaRecibidaService facturaRecibidaService;
	
	@Autowired
	protected ConfiguracionService confService;
	
	@Autowired
	protected TransportistaService transportistaService;
	
	@Autowired
	protected PedidoService pedidoService;
	
	@Autowired
	protected ClienteService clienteService;
	
	@Autowired
	protected FacturaEmitidaService facturaEmitidaService;
	
	@Autowired
	protected DependienteService dependienteService;
	
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
		
		/* Vehiculo 1*/
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4040GND");
		vehiculo.setImagen("doc/images/camionpluma.png");
		vehiculo.setCapacidad(100.);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setMMA(450.);
		
		vehiculo = vehiculoService.save(vehiculo);
		
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
				
		/* Vehiculo 2*/
		
		Vehiculo vehiculo2 = new Vehiculo();
		vehiculo2.setMatricula("E4040GND");
		vehiculo2.setImagen("doc/images/carretilaelevadora.png");
		vehiculo2.setCapacidad(20.);
		vehiculo2.setTipoVehiculo(TipoVehiculo.CARRETILLA_ELEVADORA);
		vehiculo2.setMMA(500.);
		
		vehiculoService.save(vehiculo2);
	
		//ITV de vehiculo
		
		FacturaRecibida facturaRecibidaITV2 = new FacturaRecibida();
		facturaRecibidaITV2.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITV2.setEstaPagada(true);
		facturaRecibidaITV2.setImporte(30.);
		facturaRecibidaITV2.setFechaEmision(LocalDate.of(2019, 9, 23));
		facturaRecibidaService.save(facturaRecibidaITV2);
		
		ITV itv2 = new ITV();
		itv2.setVehiculo(vehiculo2);
		itv2.setFecha(LocalDate.of(2019, 9, 23));
		itv2.setExpiracion(LocalDate.of(2021, 9, 23));
		itv2.setRecibidas(facturaRecibidaITV2);
		itv2.setResultado(ResultadoITV.FAVORABLE);
		itvService.save(itv2);
		
		//Seguro de vehiculo
		
		FacturaRecibida facturaRecibidaSeguro2 = new FacturaRecibida();
		facturaRecibidaSeguro2.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguro2.setEstaPagada(true);
		facturaRecibidaSeguro2.setImporte(220.);
		facturaRecibidaSeguro2.setFechaEmision(LocalDate.of(2019, 03, 10));
		facturaRecibidaService.save(facturaRecibidaSeguro2);

		
		Seguro seguro2 = new Seguro();
		seguro2.setCompania("Linea Directa");
		seguro2.setFechaContrato(LocalDate.of(2019, 03, 10));
		seguro2.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguro2.setRecibidas(facturaRecibidaSeguro2);
		seguro2.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro2.setVehiculo(vehiculo2);
		seguroService.save(seguro2);
				
	}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Busca un vehiculo dada su matricula -- Caso Positivo")
	void findVehiculoByMatricula() {

		Vehiculo vehiculo = vehiculoService.findByMatricula("4040GND");
		Vehiculo vehiculo2 = vehiculoService.findByMatricula("E4040GND");
		assertThat(vehiculo.getTipoVehiculo().equals(TipoVehiculo.CAMION));
		assertThat(vehiculo2.getTipoVehiculo().equals(TipoVehiculo.CARRETILLA_ELEVADORA));
	}
	
	@Test
	@Transactional
	@DisplayName("Busca un vehiculo dada su matricula -- Caso Negativo")
	void findVehiculoByMatriculaNotFound() {
		
		Vehiculo vehiculo = vehiculoService.findByMatricula("4040LNE");
		Vehiculo vehiculo2 = vehiculoService.findByMatricula("E3010UND");
		assertThat(vehiculo).isEqualTo(null);
		assertThat(vehiculo2).isEqualTo(null);
	}
	
	@Test
	@Transactional
	@DisplayName("Elimina un vehiculo dada su id -- Caso Positivo")
	void deleteVehiculoById() {

		Vehiculo v = vehiculoService.findAll().iterator().next();
		vehiculoService.deleteById(v.getId());
		List<ITV> itvs = itvService.findByVehiculo(v.getMatricula());
		assertThat(itvs.size()).isEqualTo(0);
		assertThat(vehiculoService.count()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	@DisplayName("Elimina un vehiculo dada su id -- Caso Negativo")
	void deleteVehiculoByIdNotFound() {

		assertThrows(EmptyResultDataAccessException.class, ()->{vehiculoService.deleteById(3L);});	
		assertThat(vehiculoService.count()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	@DisplayName("Elimina un vehiculo dada su matricula -- Caso Positivo")
	void deleteVehiculoByMatricula() {

		vehiculoService.deleteByMatricula("4040GND");
		vehiculoService.deleteByMatricula("E4040GND");
		assertThat(vehiculoService.count()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	@DisplayName("Elimina un vehiculo dada su matricula -- Caso Negativo")
	void deleteVehiculoByMatriculaNotFound() {

		vehiculoService.deleteByMatricula("4040LNE");
		vehiculoService.deleteByMatricula("E3010UND");
		assertThat(vehiculoService.count()).isEqualTo(2);

	}
	
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */	
	
	@Test
	@Transactional
	@DisplayName("Actualizar un vehiculo -- caso positivo")
	void updateVehiculoWithoutProblems() {
		Vehiculo v = vehiculoService.findAll().iterator().next();
		v.setMMA(413.00);
		
		assertThat(vehiculoService.count()).isEqualTo(2);
		assertThat(vehiculoService.getDisponibilidad(v.getMatricula())).isTrue();
	}
	
	@Test
	@Transactional
	@DisplayName("Guardar un nuevo vehiculo -- caso positivo")
	void addVehiculoWithoutProblems() {
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4323BLD");
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setCapacidad(300.);
		vehiculo.setMMA(201.0);
		vehiculo = vehiculoService.save(vehiculo);
				
		ITV itv = itvService.findAll().iterator().next();
		itv.setVehiculo(vehiculo);
		itvService.save(itv);
		
		Seguro seguro = seguroService.findAll().iterator().next();
		seguro.setVehiculo(vehiculo);
		seguroService.save(seguro);
		
		assertThat(vehiculoService.count()).isEqualTo(3);
		assertThat(vehiculoService.getDisponibilidad(vehiculo.getMatricula())).isTrue();
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Formato de Matricula (new) -- caso negativo")
	void saveVehiculoWithIncorrectPlate() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("1919291299SKJS");
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setCapacidad(200.);
		vehiculo.setMMA(180.0);
		
		assertThrows(MatriculaUnsupportedPatternException.class, ()->{vehiculoService.save(vehiculo);});
		assertThat(vehiculoService.findByMatricula("1919291299SKJS")).isEqualTo(null);	// Comprobacion de rollback

	}
	
	@Test
	@Transactional
	@DisplayName("RN: Formato de Matricula (update) -- caso negativo")
	void updateVehiculoWithIncorrectPlate() {
		Vehiculo vehiculo = vehiculoService.findByMatricula("4040GND");	
		Vehiculo vehiculo2 = vehiculoService.findByMatricula("E4040GND");
		vehiculo.setMatricula("4040GNDD");
		vehiculo2.setMatricula("EE4040GND");
		
		assertThrows(MatriculaUnsupportedPatternException.class, ()->{vehiculoService.save(vehiculo2);});		
		assertThrows(MatriculaUnsupportedPatternException.class, ()->{vehiculoService.save(vehiculo2);});	
	}
	
	/* * * * * * * * * * * * * * * * * * * */
	/*	PROPIEDAD DERIVADA DISPONIBILIDAD  */
	/* * * * * * * * * * * * * * * * * * * */
	@Test
	@Transactional
	@DisplayName("ITV no en vigor (new) -- caso negativo")
	void saveVehiculoWithITV() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4041GND");
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setCapacidad(200.);
		vehiculo.setMMA(180.0);
		vehiculoService.save(vehiculo);
						
		assertThat(vehiculoService.getDisponibilidad("4041GND")).isFalse();
	}
	
	@Test
	@Transactional
	@DisplayName("ITV no en vigor (update) -- caso negativo")
	void updateVehiculoWithITV() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		ITV itv = itvService.findLastITVFavorableByVehiculo(vehiculo.getMatricula());
		itv.setResultado(ResultadoITV.NEGATIVA);		
		itvService.save(itv);
		
		assertThat(vehiculoService.getDisponibilidad(vehiculo.getMatricula())).isFalse();
	}
	
	@Test
	@Transactional
	@DisplayName("Seguro no en vigor (new) -- caso negativo")
	void saveVehiculoWithSeguro() {
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4041GND");
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setCapacidad(200.);
		vehiculo.setMMA(180.0);
		
		vehiculoService.save(vehiculo);
		
		assertThat(vehiculoService.getDisponibilidad(vehiculo.getMatricula())).isFalse();
	}

	@Test
	@Transactional
	@DisplayName("Seguro no en vigor (update) -- caso negativo")
	void updateIllegalVehiculoSeguro() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		Seguro seguro = seguroService.findLastSeguroByVehiculo(vehiculo.getMatricula());
		seguro.setFechaExpiracion(LocalDate.of(2020, 2, 11));
		seguroService.save(seguro);

		assertThat(vehiculoService.getDisponibilidad(vehiculo.getMatricula())).isFalse();
	}
	
	@Test
	@Transactional
	@DisplayName("Vehiculo en uso por otro transportista -- caso negativo")
	void updateVehiculoEnUso() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();

		Transportista transportistaUsandoVehiculo = new Transportista();
		transportistaUsandoVehiculo.setName("Jose Luis Gresur");
		transportistaUsandoVehiculo.setNIF("18845878A");
		transportistaUsandoVehiculo.setEmail("e1@email.com");
		transportistaUsandoVehiculo.setTlf("696823445");
		transportistaUsandoVehiculo.setDireccion("Av. Reina Mercedes");
		transportistaUsandoVehiculo.setNSS("12 1234123890");
		transportistaUsandoVehiculo.setImage("/resources/foto.png");
		
		transportistaService.save(transportistaUsandoVehiculo);
		
		
		Transportista otroTransportista = new Transportista();
		otroTransportista.setName("Jose Luis Gresur");
		otroTransportista.setNIF("18845878W");
		otroTransportista.setEmail("e1@email.com");
		otroTransportista.setTlf("696823445");
		otroTransportista.setDireccion("Av. Reina Mercedes");
		otroTransportista.setNSS("12 1234123891");
		otroTransportista.setImage("/resources/foto1.png");
		
		transportistaService.save(transportistaUsandoVehiculo);
		
		
		Cliente cliente = new Cliente();
		cliente.setDireccion("Calle");
		cliente.setEmail("cliente@mail.es");
		cliente.setName("Jose Luis");
		cliente.setNIF("54789663T");
		cliente.setTlf("657412397");
		
		cliente = clienteService.save(cliente);
		
		
		Dependiente dependiente = new Dependiente();
		dependiente.setDireccion("C/ ejemplo");
		dependiente.setEmail("patata@gmail.com");
		dependiente.setImage("/resourdes/foto.png");
		dependiente.setName("Antonio Jose Ruiz Ruiz");
		dependiente.setNIF("12345678L");
		dependiente.setNSS("123456789012");
		dependiente.setTlf("123456789");
		
		dependiente = dependienteService.save(dependiente);
		
		List<LineaFactura> lf = new ArrayList<LineaFactura>();
		
		FacturaEmitida facturaEmitida = new FacturaEmitida();
		facturaEmitida.setCliente(cliente);
		facturaEmitida.setDependiente(dependiente);
		facturaEmitida.setEstaPagada(true);
		facturaEmitida.setImporte(320.15);
		facturaEmitida.setLineasFacturas(lf);
		
		facturaEmitida = facturaEmitidaService.save(facturaEmitida);
		
		
		Pedido pedido = new Pedido();
		pedido.setDireccionEnvio("C/ Enviamelo Aqui");
		pedido.setEstado(EstadoPedido.EN_ESPERA);
		pedido.setFacturaEmitida(facturaEmitida);
		pedido.setFechaEnvio(LocalDate.now());		
		pedido = pedidoService.save(pedido);
				
		pedido.setTransportista(transportistaUsandoVehiculo);
		pedido.setEstado(EstadoPedido.PREPARADO);
		pedido = pedidoService.save(pedido);
								
		pedido.setEstado(EstadoPedido.EN_REPARTO);
		pedido.setVehiculo(vehiculo);
		pedido = pedidoService.save(pedido);

		assertThat(vehiculoService.getDisponibilidad(vehiculo.getMatricula(), otroTransportista)).isFalse();
	}
}