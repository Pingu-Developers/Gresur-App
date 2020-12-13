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
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class ReparacionServiceTests {

	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ReparacionService reparacionService;
	
	@Autowired
	protected FacturaRecibidaService facturaRecibidaService;

	@Autowired
	protected DBUtility util;

	
	@BeforeAll
	@Transactional
	void clearDB() {
		util.clearDB();
	}
	
	/* Carga de datos para cada test */
	
	@BeforeEach
	@Transactional
	void initAll() {
		
		/* Creamos el vehiculo */
		List<Reparacion> reparaciones = new ArrayList<Reparacion>();
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4040GND");
		vehiculo.setImagen("doc/images/camionpluma.png");
		vehiculo.setCapacidad(100.);
		vehiculo.setDisponibilidad(false); //Probar
		vehiculo.setTipoVehiculo(TipoVehiculo.FURGONETA);
		vehiculo.setMMA(450.);
		/* Guardamos el vehiculo con sus reparaciones*/
		vehiculoService.save(vehiculo);
	
		
		/*Creamos varias facturas para cada repracion */
		
		FacturaRecibida facturaRecibidaReparacion = new FacturaRecibida();
		facturaRecibidaReparacion.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion.setEstaPagada(true);
		facturaRecibidaReparacion.setImporte(220.);
		facturaRecibidaReparacion.setFecha(LocalDate.of(2019, 10, 20));
		facturaRecibidaService.save(facturaRecibidaReparacion);
		
		FacturaRecibida facturaRecibidaReparacion2 = new FacturaRecibida();
		facturaRecibidaReparacion2.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion2.setEstaPagada(true);
		facturaRecibidaReparacion2.setImporte(520.);
		facturaRecibidaReparacion2.setFecha(LocalDate.of(2019, 12, 20));
		facturaRecibidaService.save(facturaRecibidaReparacion2);
		
		FacturaRecibida facturaRecibidaReparacion3 = new FacturaRecibida();
		facturaRecibidaReparacion3.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion3.setEstaPagada(true);
		facturaRecibidaReparacion3.setImporte(820.);
		facturaRecibidaReparacion3.setFecha(LocalDate.of(2020, 3, 25));
		facturaRecibidaService.save(facturaRecibidaReparacion3);
		
		
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

		vehiculo.setReparaciones(reparaciones);
		reparaciones.add(reparacion);
		reparaciones.add(reparacion2);
		reparaciones.add(reparacion3);
	}
	
	@AfterEach
	@Transactional
	void clearAll() {
		util.clearDB();
	}
	
	
	/* FIND-REMOVE TESTS */
	
	@Test
	@Transactional
	@DisplayName("findVehiculoByMatricula -- Caso Positivo")
	void findVehiculoByMatricula() {

		List<Reparacion> reparaciones = reparacionService.findByMatricula("4040GND");
		assertThat(reparaciones.size()).isGreaterThan(0);
	}
	
	@Test
	@Transactional
	@DisplayName("findVehiculoByMatricula -- Caso Negativo")
	void findVehiculoByMatriculaNotFound() {

		List<Reparacion> reparaciones = reparacionService.findByMatricula("4040LNE");
		assertThat(reparaciones.size()).isEqualTo(0);
	}

	
	/* RN TESTS */
	
	@Test
	@Transactional
	@DisplayName("RN: Fecha de entrada debe ser anterior o igual a la de salida (FechaFinNotAfterFechaInicioException)")
	void saveReparacionWithFechaInicioAfterFechaFin() {
		Reparacion reparacion = reparacionService.findAll().iterator().next();
		reparacion.setFechaEntradaTaller(LocalDate.of(2020, 10, 22));
		assertThrows(FechaFinNotAfterFechaInicioException.class, ()->{reparacionService.save(reparacion);});
	}


}
