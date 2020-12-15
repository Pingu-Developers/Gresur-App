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
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class ITVServiceTests {
	
	@Autowired
	protected ITVService itvService;
	
	@Autowired
	protected FacturaRecibidaService fraService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
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
	void initAll() { //TODO USAR NOMBRES MAS INTUITIVOS
		
		List<Reparacion> ls = new ArrayList<>();
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setCapacidad(130.00);
		vehiculo.setDisponibilidad(false);
		vehiculo.setMatricula("1526MVC");
		vehiculo.setMMA(3000.00);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculo.setReparaciones(ls);
		vehiculo = vehiculoService.save(vehiculo);
		
		FacturaRecibida fra = new FacturaRecibida();
		fra.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra.setEstaPagada(true);
		fra.setFecha(LocalDate.of(2020, 1, 10));
		fra.setImporte(65.50);
		fraService.save(fra);
		
		FacturaRecibida fra1 = new FacturaRecibida();
		fra1.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra1.setEstaPagada(true);
		fra1.setFecha(LocalDate.of(2000, 1, 10));
		fra1.setImporte(65.50);
		fraService.save(fra1);
		
		FacturaRecibida fra2 = new FacturaRecibida();
		fra2.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra2.setEstaPagada(true);
		fra2.setFecha(LocalDate.of(2005, 1, 10));
		fra2.setImporte(65.50);
		fraService.save(fra2);
		
		FacturaRecibida fra3 = new FacturaRecibida();
		fra3.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra3.setEstaPagada(true);
		fra3.setFecha(LocalDate.of(2020, 1, 10));
		fra3.setImporte(650.50);
		fraService.save(fra3);
		
		ITV itv = new ITV();
		itv.setExpiracion(LocalDate.of(2025, 1, 10));
		itv.setFecha(LocalDate.of(2020, 1, 10));
		itv.setResultado(ResultadoITV.FAVORABLE);
		itv.setVehiculo(vehiculo);
		itv.setRecibidas(fra);
		itvService.save(itv);
		
		ITV itv1 = new ITV();
		itv1.setExpiracion(LocalDate.of(2005, 1, 10));
		itv1.setFecha(LocalDate.of(2000, 1, 10));
		itv1.setResultado(ResultadoITV.FAVORABLE);
		itv1.setVehiculo(vehiculo);
		itv1.setRecibidas(fra1);
		itvService.save(itv1);
		
		ITV itv2 = new ITV();
		itv2.setExpiracion(LocalDate.of(2010, 1, 10));
		itv2.setFecha(LocalDate.of(2005, 1, 10));
		itv2.setResultado(ResultadoITV.FAVORABLE);
		itv2.setVehiculo(vehiculo);
		itv2.setRecibidas(fra2);
		itvService.save(itv2);
		
		
		Seguro seg = new Seguro();
		seg.setCompania("Linea Directa");
		seg.setFechaContrato(LocalDate.of(2020, 1, 1));
		seg.setFechaExpiracion(LocalDate.of(2025, 1, 1));
		seg.setRecibidas(fra3);
		seg.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seg.setVehiculo(vehiculo);
	}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Busca todas las ITVs por la matricula de un vehiculo -- caso positivo")
	void findITVsByVehiculoTest() {
		List<ITV> ls = itvService.findByVehiculo("1526MVC");
		assertThat(ls.size()).isEqualTo(3);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca todas las ITVs por la matricula de un vehiculo -- caso negativo")
	void findITVsByVehiculoTestNotFound() {
		List<ITV> ls = itvService.findByVehiculo("1586MQB");
		assertThat(ls.size()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca la ultima ITV favorable por matricula -- caso positivo")
	void findLastItvFavorable(){
		ITV itv = itvService.findLastITVFavorableByVehiculo("1526MVC");
		assertThat(itv.getExpiracion()).isEqualTo(LocalDate.of(2025, 1, 10));
	}
	
	@Test
	@Transactional
	@DisplayName("Busca la ultima ITV favorable por matricula -- caso negativo")
	void findLastItvFavorableNotFound(){
		ITV itv = itvService.findLastITVFavorableByVehiculo("1126MSC");
		assertThat(itv).isEqualTo(null);
	}
	
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("RN: Fechas inicio y fin incongruentes (update) -- caso positivo")
	void updateFechaExceptionPositivo() {
		ITV itv = itvService.findAll().iterator().next();
		itv.setExpiracion(LocalDate.of(2020, 1, 1));
		
		assertThat(itvService.findById(itv.getId())).isEqualTo(itv);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Fechas inicio y fin incongruentes (new) -- caso positivo")
	void saveNewFechaExceptionPositivo() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		
		FacturaRecibida fra = new FacturaRecibida();
		fra.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra.setEstaPagada(true);
		fra.setFecha(LocalDate.of(2020, 1, 12));
		fra.setImporte(65.50);
		fraService.save(fra);
		
		ITV itv = new ITV();
		itv.setExpiracion(LocalDate.of(2030, 1, 10));
		itv.setFecha(LocalDate.of(2020, 1, 12));
		itv.setResultado(ResultadoITV.FAVORABLE);
		itv.setVehiculo(vehiculo);
		itv.setRecibidas(fra);
		
		itvService.save(itv);
		
		assertThat(itvService.findLastITVVehiculo(vehiculo.getMatricula())).isEqualTo(itv);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Fechas inicio y fin incongruentes (update) -- caso negativo")
	void updateFechaException() {
		ITV itv = itvService.findAll().iterator().next();
		itv.setExpiracion(LocalDate.of(2010, 1, 1));
		itv.setFecha(LocalDate.of(2020, 12, 1));
		
		assertThrows(FechaFinNotAfterFechaInicioException.class, () -> {itvService.save(itv);}); 
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Fechas inicio y fin incongruentes (new) -- caso negativo")
	void saveNewFechaException() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
		
		FacturaRecibida fra = new FacturaRecibida();
		fra.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra.setEstaPagada(true);
		fra.setFecha(LocalDate.of(2020, 1, 12));
		fra.setImporte(65.50);
		fraService.save(fra);
		
		ITV itv = new ITV();
		itv.setExpiracion(LocalDate.of(2010, 1, 10));
		itv.setFecha(LocalDate.of(2020, 1, 12));
		itv.setResultado(ResultadoITV.FAVORABLE);
		itv.setVehiculo(vehiculo);
		itv.setRecibidas(fra);
		
		assertThrows(FechaFinNotAfterFechaInicioException.class, () -> {itvService.save(itv);});
		assertThat(itvService.findLastITVVehiculo(vehiculo.getMatricula())).isNotEqualTo(itv);
	}
	

}
