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
import org.springframework.gresur.service.exceptions.MatriculaUnsupportedPatternException;
import org.springframework.gresur.service.exceptions.VehiculoIllegalException;
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
		//TODO REVISAR
		//Vehiculo
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4040GND");
		vehiculo.setImagen("doc/images/camionpluma.png");
		vehiculo.setCapacidad(200.);
		vehiculo.setDisponibilidad(true);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setMMA(1050.);

		//ITV de vehiculo
		
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
		
		//Seguro de vehiculo
		
		FacturaRecibida facturaRecibidaSeguro = new FacturaRecibida();
		facturaRecibidaSeguro.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaSeguro.setEstaPagada(true);
		facturaRecibidaSeguro.setImporte(220.);
		facturaRecibidaSeguro.setFecha(LocalDate.of(2019, 05, 21));
		facturaRecibidaService.save(facturaRecibidaSeguro);

		
		Seguro seguro = new Seguro();
		seguro.setCompania("Linea Directa");
		seguro.setFechaContrato(LocalDate.of(2018, 05, 21));
		seguro.setFechaExpiracion(LocalDate.of(2023, 07, 21));
		seguro.setRecibidas(facturaRecibidaSeguro);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculo);
		seguroService.save(seguro);
		
		//Reparacion de vehiculo
		
		FacturaRecibida facturaRecibidaReparacion = new FacturaRecibida();
		facturaRecibidaReparacion.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion.setEstaPagada(true);
		facturaRecibidaReparacion.setImporte(220.);
		facturaRecibidaReparacion.setFecha(LocalDate.of(2019, 10, 19));
		facturaRecibidaService.save(facturaRecibidaReparacion);

		
		Reparacion reparacion = new Reparacion();
		reparacion.setFechaEntradaTaller(LocalDate.of(2019, 10, 19));
		reparacion.setFechaSalidaTaller(LocalDate.of(2019, 10, 20));
		reparacion.setRecibidas(facturaRecibidaReparacion);
		reparacion.setVehiculo(vehiculo);
		reparacionService.save(reparacion);
		
		List<Reparacion> reparaciones = new ArrayList<>();
		reparaciones.add(reparacion);
		vehiculo.setReparaciones(reparaciones);

		vehiculoService.save(vehiculo);


		//Vehiculo Carretilla elevadora
		
		Vehiculo vehiculo2 = new Vehiculo();
		vehiculo2.setMatricula("E4040GND");
		vehiculo2.setImagen("doc/images/carretilaelevadora.png");
		vehiculo2.setCapacidad(20.);
		vehiculo2.setDisponibilidad(true);
		vehiculo2.setTipoVehiculo(TipoVehiculo.CARRETILLA_ELEVADORA);
		vehiculo2.setMMA(500.);

		
		//ITV de vehiculo
		
		FacturaRecibida facturaRecibidaITV2 = new FacturaRecibida();
		facturaRecibidaITV2.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaITV2.setEstaPagada(true);
		facturaRecibidaITV2.setImporte(30.);
		facturaRecibidaITV2.setFecha(LocalDate.of(2019, 9, 23));
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
		facturaRecibidaSeguro2.setFecha(LocalDate.of(2019, 03, 10));
		facturaRecibidaService.save(facturaRecibidaSeguro2);

		
		Seguro seguro2 = new Seguro();
		seguro2.setCompania("Linea Directa");
		seguro2.setFechaContrato(LocalDate.of(2019, 03, 10));
		seguro2.setFechaExpiracion(LocalDate.of(2023, 07, 21));
		seguro2.setRecibidas(facturaRecibidaSeguro2);
		seguro2.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro2.setVehiculo(vehiculo2);
		seguroService.save(seguro2);
		
		//Reparacion de vehiculo
		
		FacturaRecibida facturaRecibidaReparacion2 = new FacturaRecibida();
		facturaRecibidaReparacion2.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibidaReparacion2.setEstaPagada(true);
		facturaRecibidaReparacion2.setImporte(220.);
		facturaRecibidaReparacion2.setFecha(LocalDate.of(2019, 2, 13));
		facturaRecibidaService.save(facturaRecibidaReparacion2);

		
		Reparacion reparacion2 = new Reparacion();
		reparacion2.setFechaEntradaTaller(LocalDate.of(2019, 2, 13));
		reparacion2.setFechaSalidaTaller(LocalDate.of(2019, 2, 25));
		reparacion2.setRecibidas(facturaRecibidaReparacion2);
		reparacion2.setVehiculo(vehiculo2);
		reparacionService.save(reparacion2);
		
		List<Reparacion> reparaciones2 = new ArrayList<>();
		reparaciones.add(reparacion2);
		vehiculo.setReparaciones(reparaciones2);
		vehiculoService.save(vehiculo2);

		
	}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("findVehiculoByMatricula -- Caso Positivo")
	void findVehiculoByMatricula() {

		Vehiculo vehiculo = vehiculoService.findByMatricula("4040GND");
		Vehiculo vehiculo2 = vehiculoService.findByMatricula("E4040GND");
		assertThat(vehiculo.getTipoVehiculo().equals(TipoVehiculo.CAMION));
		assertThat(vehiculo2.getTipoVehiculo().equals(TipoVehiculo.CARRETILLA_ELEVADORA));
	}
	
	@Test
	@Transactional
	@DisplayName("findVehiculoByMatricula -- Caso Negativo")
	void findVehiculoByMatriculaNotFound() {

		Vehiculo vehiculo = vehiculoService.findByMatricula("4040LNE");
		Vehiculo vehiculo2 = vehiculoService.findByMatricula("E3010UND");
		assertThat(vehiculo).isEqualTo(null);
		assertThat(vehiculo2).isEqualTo(null);
	}
	
	@Test
	@Transactional
	@DisplayName("deleteVehiculoByMatricula -- Caso Positivo")
	void deleteVehiculoByMatricula() {

		vehiculoService.deleteByMatricula("4040GND");
//		vehiculoService.deleteByMatricula("E4040GND");
		assertThat(vehiculoService.count()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	@DisplayName("deleteVehiculoByMatricula -- Caso Negativo")
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
	@DisplayName("RN: Formato de Matricula (MatriculaUnsupportedPatternException)")
	void saveVehiculoWithIncorrectPlate() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		vehiculo.setMatricula("4040GNDD");
		Vehiculo vehiculo2 = vehiculoService.findAll().iterator().next();
		vehiculo.setMatricula("EE4040GND");
		assertThrows(MatriculaUnsupportedPatternException.class, ()->{vehiculoService.save(vehiculo);});
		assertThrows(MatriculaUnsupportedPatternException.class, ()->{vehiculoService.save(vehiculo2);});
		assertThat(vehiculoService.findByMatricula("4040GNDD")).isEqualTo(null);
		assertThat(vehiculoService.findByMatricula("EE4040GND")).isEqualTo(null);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: ITV no en vigor (VehiculoIllegalException)")
	void saveVehiculoWithITV() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		ITV itv = itvService.findAll().iterator().next();
		itv.setExpiracion(LocalDate.of(2020, 12, 11));
		itvService.save(itv);
		
		Vehiculo vehiculo2 = vehiculoService.findAll().iterator().next();
		ITV itv2 = itvService.findAll().iterator().next();
		itv2.setExpiracion(LocalDate.of(2020, 12, 11));
		itvService.save(itv2);
		
		assertThrows(VehiculoIllegalException.class, ()->{vehiculoService.save(vehiculo);});
		assertThrows(VehiculoIllegalException.class, ()->{vehiculoService.save(vehiculo2);});
	}


	@Test
	@Transactional
	@DisplayName("RN: Seguro no en vigor (VehiculoIllegalException)")
	void saveIllegalVehiculoSeguro() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		Seguro seguro = seguroService.findAll().iterator().next();
		seguro.setFechaExpiracion(LocalDate.of(2020, 2, 11));
		seguroService.save(seguro);
		
		Vehiculo vehiculo2 = vehiculoService.findAll().iterator().next();
		Seguro seguro2 = seguroService.findAll().iterator().next();
		seguro2.setFechaExpiracion(LocalDate.of(2020, 2, 11));
		seguroService.save(seguro2);

		assertThrows(VehiculoIllegalException.class, ()->{vehiculoService.save(vehiculo);});
		assertThrows(VehiculoIllegalException.class, ()->{vehiculoService.save(vehiculo2);});
	}

}
