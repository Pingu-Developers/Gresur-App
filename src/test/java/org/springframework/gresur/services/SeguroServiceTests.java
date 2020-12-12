package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class SeguroServiceTests {
	
	@Autowired
	protected SeguroService seguroService;
	
	@Autowired
	protected FacturaRecibidaService fraService;
	
	@Autowired
	protected VehiculoService vehiculoService;
	
	@Autowired
	protected ProveedorService proveedorService;
	
	@BeforeEach
	@Transactional
	void initAll() {
			
		Proveedor prov = new Proveedor();
		prov.setDireccion("Calle");
		prov.setEmail("mail@mail.es");
		prov.setIBAN("ES6621000418401234567891");
		prov.setName("Linea Directa Aseguradora S.A.");
		prov.setNIF("A80871031");
		prov.setTlf("917001001");
		proveedorService.save(prov);
		
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setCapacidad(130.00);
		vehiculo.setDisponibilidad(false);
		vehiculo.setMatricula("1526MVC");
		vehiculo.setMMA(3000.00);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		vehiculoService.save(vehiculo);
		
		FacturaRecibida fra = new FacturaRecibida();
		fra.setConcepto(Concepto.GASTOS_VEHICULOS);
		fra.setEstaPagada(true);
		fra.setFecha(LocalDate.of(2020, 1, 10));
		fra.setImporte(300.50);
		fra.setProveedor(prov);
		fraService.save(fra);
		
		Seguro seguro = new Seguro();
		seguro.setCompania("Linea Directa");
		seguro.setFechaContrato(LocalDate.of(2020, 1, 10));
		seguro.setFechaExpiracion(LocalDate.of(2100, 12, 12));
		seguro.setRecibidas(fra);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		seguro.setVehiculo(vehiculo);
		seguroService.save(seguro);
	}
	
	@AfterEach
	@Transactional
	void clearAll() {
		seguroService.deleteAll();
	}
	
	@Test
	@Transactional
	void findSeguroById() {
		Long id = seguroService.findAll().iterator().next().getId();
		Seguro seguro = seguroService.findById(id);
		assertThat(seguro.getCompania()).isEqualTo("Linea Directa");
		
	}

}
