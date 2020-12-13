package org.springframework.gresur.services;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.Concepto;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoSeguro;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.service.ClienteService;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.service.exceptions.MatriculaUnsupportedPatternException;
import org.springframework.gresur.service.exceptions.SalarioMinimoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
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
	
	//Carga de datos para Test 
	
	@BeforeEach
	@Transactional
	void initAll() {
		
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

	
	}
	
	//Borramos los datos despues de realizar el test correspondiente
	
	@AfterEach
	@Transactional
	void clearAll(){
		vehiculoService.deleteAll();;
	}

	//Realizamos los test de los services
	
	@Test
	@Transactional
	@DisplayName("RN: Formato de Matricula")
	void updateVehiculoWithCorrectPlate() {
		Vehiculo vehiculo = vehiculoService.findAll().iterator().next();
	//	RuntimeException exception = assertThrows(MatriculaUnsupportedPatternException.class, () -> {vehiculoService.save(vehiculo);});
	//	System.out.println("---------------------------------------------"+ vehiculo);
	//    assertTrue(exception.getMessage().contains("matricula"));
		vehiculoService.save(vehiculo);

	}

}
