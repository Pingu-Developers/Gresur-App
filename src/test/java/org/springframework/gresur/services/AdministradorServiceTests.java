package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoJornada;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.gresur.service.ContratoService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class AdministradorServiceTests {
	
	@Autowired
	protected AdministradorService administradorService;
	
	@Autowired
	protected DependienteService dependienteService;
	
	@Autowired
	protected ContratoService contratoService;
	
	@Autowired
	protected ConfiguracionService configService;
	
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
		Configuracion cfg = new Configuracion();
		cfg.setSalarioMinimo(900.);
		cfg.setNumMaxNotificaciones(10);
		cfg.setFacturaEmitidaSeq(0L);
		cfg.setFacturaRecibidaSeq(0L);
		cfg.setFacturaEmitidaRectSeq(0L);
		cfg.setFacturaRecibidaRectSeq(0L);
		configService.save(cfg);
		
		//CREACION DE UN DEPENDIENTE
		Dependiente dp = new Dependiente();
		dp.setName("Dependiente de prueba");
		dp.setNIF("18845878W");
		dp.setEmail("e2@email.com");
		dp.setTlf("696823449");
		dp.setDireccion("Av. Reina Mercedes");
		dp.setNSS("12 1234123891");
		dp.setImage("/resources/foto1.png");
				
		dp = dependienteService.save(dp);
		
		// CREACION DEL ADMINISTRADOR
		Administrador adm = new Administrador();
		adm.setName("Jose Luis Gresur");
		adm.setNIF("18845878A");
		adm.setEmail("e1@email.com");
		adm.setTlf("696823445");
		adm.setDireccion("Av. Reina Mercedes");
		adm.setNSS("12 1234123890");
		adm.setImage("/resources/foto.png");
		
		adm = administradorService.save(adm);
		
		//CREACION DE UN CONTRATO
		Contrato cont = new Contrato();
		cont.setEntidadBancaria("IBERBANK");
		cont.setFechaInicio(LocalDate.of(2017, 12, 31));
		cont.setFechaFin(LocalDate.of(2100, 12, 31));
		cont.setNomina(2563.99);
		cont.setTipoJornada(TipoJornada.COMPLETA);
		cont.setPersonal(adm);
		
		cont = contratoService.save(cont);
	}
	
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("Buscar Administrador por su NIF -- caso positivo")
	void findAdminByNif() {
		Administrador adm = administradorService.findByNIF("18845878A");
		assertThat(adm.getName()).isEqualTo("Jose Luis Gresur");
	}
	
	@Test
	@Transactional
	@DisplayName("Buscar Administrador por su NIF -- caso negativo")
	void findAdminByNifNotFound() {
		Administrador adm = administradorService.findByNIF("18845878C");
		assertThat(adm).isEqualTo(null);
	}
	
	@Test
	@Transactional
	@DisplayName("Buscar a todo el personal")
	void findAllPersonal() {
		List<Personal> lp = administradorService.findAllPersonal();
		assertThat(lp.size()).isEqualTo(2);
		assertThat(lp.get(0).getId()).isNotEqualTo(lp.get(1).getId());
		assertThat(lp.get(0).getClass()).isEqualTo(Administrador.class);
		assertThat(lp.get(1).getClass()).isEqualTo(Dependiente.class);
	}
	

	/* * * * * * * * * * * * * *
	 *   DELETE CASCADE TESTS  *
	 * * * * * * * * * * * * * */
	
	@Test
	@Transactional	
	@DisplayName("Borrar Administrador por su NIF -- caso positivo")
	void deleteAdminByNIF() {
		administradorService.deleteByNIF("18845878A");
		assertThat(administradorService.count()).isEqualTo(0);
		assertThat(contratoService.count()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	@DisplayName("Borrar Administrador por su NIF -- caso negativo")
	void deleteAdminByNIFNotFound() {
		administradorService.deleteByNIF("18845878S");
		assertThat(administradorService.count()).isEqualTo(1);
		assertThat(contratoService.count()).isEqualTo(1);
	}
	
}
