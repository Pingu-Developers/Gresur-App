package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class EncargadoDeAlmacenServiceTests {
	
	@Autowired
	protected EncargadoDeAlmacenService encargadoService;
	
	@Autowired
	protected AlmacenService almacenService;
	
	@BeforeEach
	@Transactional
	void initAll() {
		Almacen alm = new Almacen();
		alm.setCapacidad(30.0);
		alm.setDireccion("Los Algodonales");
		
		almacenService.save(alm);
		
		EncargadoDeAlmacen enc = new EncargadoDeAlmacen();
		enc.setName("Jose Luis Gresur");
		enc.setNIF("18845878A");
		enc.setEmail("e1@email.com");
		enc.setTlf("696823445");
		enc.setDireccion("Av. Reina Mercedes");
		enc.setNSS("12 1234123890");
		enc.setImage("/resources/foto.png");
		enc.setAlmacen(alm);
		
		encargadoService.save(enc);
	}
	@AfterEach
	@Transactional
	void clearAll() {
		encargadoService.deleteAll();
		almacenService.deletAll();
	}
	
	//Tests
	
	@Test
	@Transactional	
	void findAdminByNif() {
		EncargadoDeAlmacen adm = encargadoService.findByNIF("18845878A");
		assertThat(adm.getName()).isEqualTo("Jose Luis Gresur");
	}
	
	@Test
	@Transactional	
	void deleteAdminByNIF() {
		encargadoService.deleteByNIF("18845878A");
		assertThat(encargadoService.count()).isEqualTo(0);
	}

}