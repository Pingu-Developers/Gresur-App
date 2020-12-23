package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
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
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoSeguro;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.service.exceptions.FechaFinNotAfterFechaInicioException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class SeguroServiceTests {
	
	@Autowired
	protected SeguroService seguroService;
	
	@Autowired
	protected FacturaRecibidaService fraService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ProveedorService proveedorService;
	
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
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setCapacidad(130.00);
		vehiculo.setMatricula("1526MVC");
		vehiculo.setMMA(3000.00);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculoService.save(vehiculo);
		
		Proveedor prov = new Proveedor();
		prov.setDireccion("Calle");
		prov.setEmail("mail@mail.es");
		prov.setIBAN("ES6621000418401234567891");
		prov.setName("Linea Directa Aseguradora S.A.");
		prov.setNIF("80871031A");
		prov.setTlf("917001001");
		proveedorService.save(prov);		
		
		FacturaRecibida fra = new FacturaRecibida();
		fra.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra.setEstaPagada(true);
		fra.setFecha(LocalDate.of(2020, 1, 10));
		fra.setImporte(300.50);
		fra.setProveedor(prov);
		fraService.save(fra);
		
		FacturaRecibida fra1 = new FacturaRecibida();
		fra1.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra1.setEstaPagada(true);
		fra1.setFecha(LocalDate.of(1990, 1, 10));
		fra1.setImporte(300.50);
		fra1.setProveedor(prov);
		fraService.save(fra1);
		
		Seguro seguro = new Seguro();
		seguro.setCompania("Linea Directa");
		seguro.setFechaContrato(LocalDate.of(2020, 1, 10));
		seguro.setFechaExpiracion(LocalDate.of(2100, 12, 12));
		seguro.setRecibidas(fra);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculo);
		seguroService.save(seguro);
		
		Seguro seguro1 = new Seguro();
		seguro1.setCompania("Linea Directa");
		seguro1.setFechaContrato(LocalDate.of(1990, 1, 10));
		seguro1.setFechaExpiracion(LocalDate.of(2004, 12, 12));
		seguro1.setRecibidas(fra1);
		seguro1.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro1.setVehiculo(vehiculo);
		seguroService.save(seguro1);
			
	}

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Busca los seguros de un vehiculo en funcion de su matricula -- caso positivo")
	void findSegurosByMatricula() {
		List<Seguro> ls = seguroService.findByVehiculo("1526MVC");
		assertThat(ls.size()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca los seguros de un vehiculo en funcion de su matricula -- caso negativo")
	void findSegurosByMatriculaNotFound() {
		List<Seguro> ls = seguroService.findByVehiculo("1126MSC");
		assertThat(ls.size()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	@DisplayName("Busca el ultimo seguro de un vehiculo -- caso positivo")
	void findLastSeguroByVehiculo() {
		Seguro lastSeguro = seguroService.findLastSeguroByVehiculo("1526MVC");
		assertThat(lastSeguro.getFechaContrato()).isEqualTo(LocalDate.of(2020, 1, 10));
	}
	
	@Test
	@Transactional
	@DisplayName("Busca el ultimo seguro de un vehiculo -- caso negativo")
	void findLastSeguroByVehiculoNotFound() {
		Seguro lastSeguro = seguroService.findLastSeguroByVehiculo("1521MSC");
		assertThat(lastSeguro).isEqualTo(null);
	}
	
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("RN: Fecha expiracion debe ser posterior a fecha contratacion (update) -- caso positivo")

	void updateSeguroFechaExceptionPositive() {
		Seguro seguro = seguroService.findAll().iterator().next();
		seguro.setFechaContrato(LocalDate.of(2020, 1, 1));
		seguro.setFechaExpiracion(LocalDate.of(2030, 1, 1));
		seguroService.save(seguro);
		
		assertThat(seguroService.findAll().iterator().next().getFechaExpiracion()).isEqualTo(LocalDate.of(2030, 1, 1));
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Fecha expiracion debe ser posterior a fecha contratacion (new) -- caso positivo")
	void addSeguroFechaExceptionPositive() {
		FacturaRecibida fra = new FacturaRecibida();
		fra.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra.setEstaPagada(true);
		fra.setFecha(LocalDate.of(2020, 1, 10));
		fra.setImporte(300.50);
		fra.setProveedor(proveedorService.findByNIF("80871031A"));
		fra = fraService.save(fra);
		
		Seguro seguro = new Seguro();
		seguro.setCompania("Linea Directa");
		seguro.setFechaExpiracion(LocalDate.of(2030, 1, 10));
		seguro.setFechaContrato(LocalDate.of(2020, 1, 10));
		seguro.setRecibidas(fra);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculoService.findByMatricula("1526MVC"));
		seguro = seguroService.save(seguro);
		
		assertThat(fraService.findByNumFactura(fra.getId())).isEqualTo(fra);
		assertThat(seguroService.findById(seguro.getId())).isEqualTo(seguro);
	}
	
	
	@Test
	@Transactional
	@DisplayName("RN: Fecha expiracion debe ser posterior a fecha contratacion (update) -- caso negativo")

	void updateSeguroFechaException() {
		Seguro seguro = seguroService.findAll().iterator().next();
		seguro.setFechaContrato(LocalDate.of(2020, 1, 1));
		seguro.setFechaExpiracion(LocalDate.of(2005, 1, 1));
		
		assertThrows(FechaFinNotAfterFechaInicioException.class, () -> {seguroService.save(seguro);}); 
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Fecha expiracion debe ser posterior a fecha contratacion (new) -- caso negativo")
	void addSeguroFechaException() {
		FacturaRecibida fra = new FacturaRecibida();
		fra.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra.setEstaPagada(true);
		fra.setFecha(LocalDate.now());
		fra.setImporte(300.50);
		fra.setProveedor(proveedorService.findByNIF("80871031A"));
		fraService.save(fra);
		
		Seguro seguro = new Seguro();
		seguro.setCompania("Linea Directa");
		seguro.setFechaExpiracion(LocalDate.now().minusDays(1L));
		seguro.setFechaContrato(fra.getFecha());
		seguro.setRecibidas(fra);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculoService.findByMatricula("1526MVC"));
		
		assertThrows(FechaFinNotAfterFechaInicioException.class, () -> {seguroService.save(seguro);});
		Seguro lastSeguroBD = seguroService.findLastSeguroByVehiculo(seguro.getVehiculo().getMatricula());
		assertThat(lastSeguroBD.getRecibidas().getFecha()).isNotEqualTo(LocalDate.now());
		assertThat(lastSeguroBD).isNotEqualTo(seguro);
	}
}
