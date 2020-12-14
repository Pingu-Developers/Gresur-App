package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
		
		/* Vehiculo 1*/
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setMatricula("4040GND");
		vehiculo.setImagen("doc/images/camionpluma.png");
		vehiculo.setCapacidad(100.);
		vehiculo.setDisponibilidad(false); 
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setMMA(450.);
		vehiculo.setReparaciones(new ArrayList<Reparacion>());
		
		vehiculo = vehiculoService.save(vehiculo);
		
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
		seguro.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguro.setRecibidas(facturaRecibidaSeguro);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculo);
		seguroService.save(seguro);	
		
		
		/* Como tiene ITV Favorable y Seguro en vigor*/		
//		vehiculo.setDisponibilidad(true);
//		vehiculoService.save(vehiculo);
		
		
		/* Vehiculo 2*/
		
		Vehiculo vehiculo2 = new Vehiculo();
		vehiculo2.setMatricula("E4040GND");
		vehiculo2.setImagen("doc/images/carretilaelevadora.png");
		vehiculo2.setCapacidad(20.);
		vehiculo2.setDisponibilidad(false);
		vehiculo2.setTipoVehiculo(TipoVehiculo.CARRETILLA_ELEVADORA);
		vehiculo2.setMMA(500.);
		vehiculo2.setReparaciones(new ArrayList<Reparacion>());
		
		vehiculoService.save(vehiculo2);
	
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
		seguro2.setFechaExpiracion(LocalDate.of(2025, 07, 21));
		seguro2.setRecibidas(facturaRecibidaSeguro2);
		seguro2.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro2.setVehiculo(vehiculo2);
		seguroService.save(seguro2);
		
		/* Como tiene ITV Favorable y Seguro en vigor*/		
//		vehiculo2.setDisponibilidad(true);
//		vehiculoService.save(vehiculo2);
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
		//TODO Cuando se borra un vehiculo no se borran sus itvs ni seguros por lo que hay que borrar estas antes
		
		itvService.deleteAll();
		seguroService.deleteAll();
		vehiculoService.deleteByMatricula("4040GND");
		vehiculoService.deleteByMatricula("E4040GND");
		assertThat(vehiculoService.count()).isEqualTo(0);
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
		Vehiculo vehiculo = vehiculoService.findByMatricula("4040GND");
		vehiculo.setMatricula("4040GNDD");
		Vehiculo vehiculo2 = vehiculoService.findByMatricula("E4040GND");
		vehiculo2.setMatricula("EE4040GND");
		assertThrows(MatriculaUnsupportedPatternException.class, ()->{vehiculoService.save(vehiculo);});
		assertThrows(MatriculaUnsupportedPatternException.class, ()->{vehiculoService.save(vehiculo2);});

	}
	
	@Test
	@Transactional
	@DisplayName("RN: ITV no en vigor (VehiculoIllegalException)")
	void saveVehiculoWithITV() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		ITV itv = itvService.findLastITVFavorableByVehiculo(vehiculo.getMatricula());
		itv.setResultado(ResultadoITV.NEGATIVA);		

		Vehiculo vehiculo2 = vehiculoService.findAll().iterator().next();
		ITV itv2 = itvService.findLastITVFavorableByVehiculo(vehiculo2.getMatricula());;
		itv2.setResultado(ResultadoITV.NEGATIVA);
		
		assertThrows(VehiculoIllegalException.class, ()->{itvService.save(itv);});
		assertThrows(VehiculoIllegalException.class, ()->{itvService.save(itv2);});
	}


	@Test
	@Transactional
	@DisplayName("RN: Seguro no en vigor (VehiculoIllegalException)")
	void saveIllegalVehiculoSeguro() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		Seguro seguro = seguroService.findLastSeguroByVehiculo(vehiculo.getMatricula());
		seguro.setFechaExpiracion(LocalDate.of(2020, 2, 11));
		
		Vehiculo vehiculo2 = vehiculoService.findAll().iterator().next();
		Seguro seguro2 = seguroService.findLastSeguroByVehiculo(vehiculo2.getMatricula());
		seguro2.setFechaExpiracion(LocalDate.of(2020, 2, 11));

		assertThrows(VehiculoIllegalException.class, ()->{seguroService.save(seguro);});
		assertThrows(VehiculoIllegalException.class, ()->{seguroService.save(seguro2);});
	}

}
