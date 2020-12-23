package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.gresur.model.Concepto;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoSeguro;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class ReparacionServiceTests {

	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ReparacionService reparacionService;
	
	@Autowired
	protected FacturaRecibidaService facturaRecibidaService;
	
	@Autowired
	protected SeguroService seguroService;
	
	@Autowired
	protected ITVService itvService;

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
		
		/* Creamos el vehiculo */
		List<Reparacion> reparaciones = new ArrayList<Reparacion>();
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4040GND");
		vehiculo.setImagen("doc/images/camionpluma.png");
		vehiculo.setCapacidad(100.);
		vehiculo.setTipoVehiculo(TipoVehiculo.FURGONETA);
		vehiculo.setMMA(450.);
		
		/* Guardamos el vehiculo*/
		vehiculoService.save(vehiculo);
		
		/* ITV */
		
		FacturaRecibida facturaRecibidaITV = new FacturaRecibida();
		facturaRecibidaITV.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITV.setEstaPagada(true);
		facturaRecibidaITV.setImporte(50.);
		facturaRecibidaITV.setFecha(LocalDate.of(2019, 10, 21));
		facturaRecibidaService.save(facturaRecibidaITV);
				
		ITV itv = new ITV();
		itv.setVehiculo(vehiculo);
		itv.setFecha(LocalDate.of(2019, 10, 21));
		itv.setExpiracion(LocalDate.of(2022, 10, 21));
		itv.setRecibidas(facturaRecibidaITV);
		itv.setResultado(ResultadoITV.FAVORABLE);
		itvService.save(itv);
				
		/* SEGURO */
				
		FacturaRecibida facturaRecibidaSeguro = new FacturaRecibida();
		facturaRecibidaSeguro.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguro.setEstaPagada(true);
		facturaRecibidaSeguro.setImporte(220.);
		facturaRecibidaSeguro.setFecha(LocalDate.of(2019, 05, 21));
		facturaRecibidaService.save(facturaRecibidaSeguro);

				
		Seguro seguro = new Seguro();
		seguro.setCompania("Linea Directa");
		seguro.setFechaContrato(LocalDate.of(2018, 05, 21));
		seguro.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguro.setRecibidas(facturaRecibidaSeguro);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculo);
		seguroService.save(seguro);			
		
		/*Creamos varias facturas para cada repracion */
		
		FacturaRecibida facturaRecibidaReparacion = new FacturaRecibida();
		facturaRecibidaReparacion.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion.setEstaPagada(true);
		facturaRecibidaReparacion.setImporte(220.);
		facturaRecibidaReparacion.setFecha(LocalDate.of(2019, 10, 20));
		facturaRecibidaReparacion = facturaRecibidaService.save(facturaRecibidaReparacion);
		
		FacturaRecibida facturaRecibidaReparacion2 = new FacturaRecibida();
		facturaRecibidaReparacion2.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion2.setEstaPagada(true);
		facturaRecibidaReparacion2.setImporte(520.);
		facturaRecibidaReparacion2.setFecha(LocalDate.of(2019, 12, 20));
		facturaRecibidaReparacion2 = facturaRecibidaService.save(facturaRecibidaReparacion2);
		
		FacturaRecibida facturaRecibidaReparacion3 = new FacturaRecibida();
		facturaRecibidaReparacion3.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion3.setEstaPagada(true);
		facturaRecibidaReparacion3.setImporte(820.);
		facturaRecibidaReparacion3.setFecha(LocalDate.of(2020, 3, 25));
		facturaRecibidaReparacion3 = facturaRecibidaService.save(facturaRecibidaReparacion3);
		
		
		/* Creamos varias reparaciones del vehiculo */

		Reparacion reparacion = new Reparacion();
		reparacion.setFechaEntradaTaller(LocalDate.of(2019, 10, 19));
		reparacion.setFechaSalidaTaller(LocalDate.of(2019, 10, 20));
		reparacion.setRecibidas(facturaRecibidaReparacion);
		reparacion.setVehiculo(vehiculo);
		reparacionService.save(reparacion);
		
		Reparacion reparacion2 = new Reparacion();
		reparacion2.setFechaEntradaTaller(LocalDate.of(2019, 12, 10));
		reparacion2.setFechaSalidaTaller(LocalDate.of(2019, 12, 20));
		reparacion2.setRecibidas(facturaRecibidaReparacion2);
		reparacion2.setVehiculo(vehiculo);
		reparacionService.save(reparacion2);
		
		Reparacion reparacion3 = new Reparacion();
		reparacion3.setFechaEntradaTaller(LocalDate.of(2020, 3, 1));
		reparacion3.setFechaSalidaTaller(LocalDate.of(2020, 3, 25));
		reparacion3.setRecibidas(facturaRecibidaReparacion3);
		reparacion3.setVehiculo(vehiculo);
		reparacionService.save(reparacion3);

		reparaciones.add(reparacion);
		reparaciones.add(reparacion2);
		reparaciones.add(reparacion3);
	}

	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Busca un vehiculo dado su matricula -- Caso Positivo")
	void findVehiculoByMatricula() {

		List<Reparacion> reparaciones = reparacionService.findByMatricula("4040GND");
		assertThat(reparaciones.size()).isGreaterThan(0);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca un vehiculo dado su matricula -- Caso Negativo")
	void findVehiculoByMatriculaNotFound() {

		List<Reparacion> reparaciones = reparacionService.findByMatricula("4040LNE");
		assertThat(reparaciones.size()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca la ultima reparacion de un vehiculo dado -- Caso positivo")
	void findLastReparacionByVehiculo() {
		
		/* Pongo el vehiculo en reparacion */
		Vehiculo vehiculo = vehiculoService.findByMatricula("4040GND");
		
		FacturaRecibida facturaRecibidaReparacionActual = new FacturaRecibida();
		facturaRecibidaReparacionActual.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacionActual.setEstaPagada(true);
		facturaRecibidaReparacionActual.setImporte(820.);
		facturaRecibidaReparacionActual.setFecha(LocalDate.now());
		facturaRecibidaReparacionActual = facturaRecibidaService.save(facturaRecibidaReparacionActual);
		
		Reparacion reparacionActual = new Reparacion();
		reparacionActual.setFechaEntradaTaller(LocalDate.now());
		reparacionActual.setFechaSalidaTaller(null);
		reparacionActual.setRecibidas(facturaRecibidaReparacionActual);
		reparacionActual.setVehiculo(vehiculo);
		reparacionService.save(reparacionActual);
				
		Reparacion ultimaReparacion = reparacionService.findLastReparacionByVehiculo("4040GND");
		assertThat(ultimaReparacion.getFechaSalidaTaller()).isNull();
		assertThat(vehiculoService.getDisponibilidad("4040GND")).isFalse();
	}
	
	@Test
	@Transactional
	@DisplayName("Busca la ultima reparacion de un vehiculo dado -- Caso negativo")
	void findLastReparacionByVehiculoNotFound() {
		
		Reparacion ultimaReparacion = reparacionService.findLastReparacionByVehiculo("1829LSK");
		assertThat(ultimaReparacion).isNull();
		assertThat(vehiculoService.findByMatricula("1829LSK")).isNull();
	}

	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("RN: Fecha de entrada debe ser anterior o igual a la de salida (update) -- caso positivo")
	void updateReparacionWithFechaInicioAfterFechaFinPositive() {
		Reparacion reparacion = reparacionService.findAll().iterator().next();
		reparacion.setFechaEntradaTaller(LocalDate.of(2018, 12, 22));
		reparacionService.save(reparacion);

		assertThat(reparacionService.findAll().iterator().next().getFechaEntradaTaller()).isEqualTo(LocalDate.of(2018, 12, 22));
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Fecha de entrada debe ser anterior o igual a la de salida (new) -- caso positivo")
	void saveReparacionWithFechaInicioAfterFechaFinPositive() {
		Reparacion reparacion = reparacionService.findAll().iterator().next();
		
		FacturaRecibida facturaRecibidaReparacion4 = new FacturaRecibida();
		facturaRecibidaReparacion4.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion4.setEstaPagada(true);
		facturaRecibidaReparacion4.setImporte(820.);
		facturaRecibidaReparacion4.setFecha(LocalDate.now());
		facturaRecibidaService.save(facturaRecibidaReparacion4);
		
		Reparacion reparacionFechaCongruente = new Reparacion();
		reparacionFechaCongruente.setFechaEntradaTaller(LocalDate.now());
		reparacionFechaCongruente.setFechaSalidaTaller(LocalDate.now().plusDays(1L));
		reparacionFechaCongruente.setRecibidas(facturaRecibidaReparacion4);
		reparacionFechaCongruente.setVehiculo(reparacion.getVehiculo());
		
		reparacionFechaCongruente = reparacionService.save(reparacionFechaCongruente);

		assertThat(reparacionService.findById(reparacionFechaCongruente.getId())).isEqualTo(reparacionFechaCongruente);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Fecha de entrada debe ser anterior o igual a la de salida (update) -- caso negativo")
	void updateReparacionWithFechaInicioAfterFechaFin() {
		Reparacion reparacion = reparacionService.findAll().iterator().next();
		reparacion.setFechaEntradaTaller(LocalDate.of(2020, 10, 22));
		
		assertThrows(FechaFinNotAfterFechaInicioException.class, ()->{reparacionService.save(reparacion);});	
	}

	@Test
	@Transactional
	@DisplayName("RN: Fecha de entrada debe ser anterior o igual a la de salida (new) -- caso negativo")
	void saveReparacionWithFechaInicioAfterFechaFin() {
		Reparacion reparacion = reparacionService.findAll().iterator().next();
		
		FacturaRecibida facturaRecibidaReparacion4 = new FacturaRecibida();
		facturaRecibidaReparacion4.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion4.setEstaPagada(true);
		facturaRecibidaReparacion4.setImporte(820.);
		facturaRecibidaReparacion4.setFecha(LocalDate.now());
		facturaRecibidaService.save(facturaRecibidaReparacion4);
		
		Reparacion reparacionFechaCongruente = new Reparacion();
		reparacionFechaCongruente.setFechaEntradaTaller(LocalDate.now());
		reparacionFechaCongruente.setFechaSalidaTaller(LocalDate.now().minusDays(1L));
		reparacionFechaCongruente.setRecibidas(facturaRecibidaReparacion4);
		reparacionFechaCongruente.setVehiculo(reparacion.getVehiculo());
		
		assertThrows(FechaFinNotAfterFechaInicioException.class, ()->{reparacionService.save(reparacionFechaCongruente);});
		List<Reparacion> lr = reparacionService.findByMatricula(reparacion.getVehiculo().getMatricula());
		assertThat(lr.get(lr.size()-1)).isNotEqualTo(reparacionFechaCongruente);
	}
	
	/* * * * * * * * * * * * * * * *
	 *    FUNCIONALIDADES TESTS    *
	 * * * * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Si la fecha de salida es posterior a hoy el vehiculo pasa a estar no disponible")
	void saveReparacionVehiculoNoDisponiblePositive() {
		
		Reparacion reparacion = reparacionService.findAll().iterator().next();
		
		FacturaRecibida facturaRecibidaReparacion4 = new FacturaRecibida();
		facturaRecibidaReparacion4.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion4.setEstaPagada(true);
		facturaRecibidaReparacion4.setImporte(820.);
		facturaRecibidaReparacion4.setFecha(LocalDate.now());
		facturaRecibidaService.save(facturaRecibidaReparacion4);
		
		Reparacion reparacionFechaCongruente = new Reparacion();
		reparacionFechaCongruente.setFechaEntradaTaller(LocalDate.now());
		reparacionFechaCongruente.setFechaSalidaTaller(LocalDate.now().plusDays(1L));
		reparacionFechaCongruente.setRecibidas(facturaRecibidaReparacion4);
		reparacionFechaCongruente.setVehiculo(reparacion.getVehiculo());
		
		assertThat(vehiculoService.getDisponibilidad(reparacion.getVehiculo().getMatricula())).isTrue();
		
		reparacionFechaCongruente = reparacionService.save(reparacionFechaCongruente);

		assertThat(vehiculoService.getDisponibilidad(reparacion.getVehiculo().getMatricula())).isFalse();
	}
}
