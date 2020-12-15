package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.*;
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
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.model.TipoJornada;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.gresur.service.ContratoService;
import org.springframework.gresur.service.exceptions.SalarioMinimoException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(value = Lifecycle.PER_CLASS)
class ContratoServiceTests {
	@Autowired
	protected AdministradorService administradorService;
	
	@Autowired
	protected ContratoService contratoService;
	
	@Autowired
	protected ConfiguracionService confService;
	
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
		Configuracion conf = new Configuracion();
		conf.setSalarioMinimo(900.00);
		conf.setNumMaxNotificaciones(100);
		
		confService.save(conf);
		
		//CREACION DE ADMINISTRADOR
		Administrador adm = new Administrador();
		adm.setName("Jose Luis Gresur");
		adm.setNIF("18845878A");
		adm.setEmail("e1@email.com");
		adm.setTlf("696823445");
		adm.setDireccion("Av. Reina Mercedes");
		adm.setNSS("12 1234123890");
		adm.setImage("/resources/foto.png");
		
		administradorService.save(adm);
		
		//CREACION DE CONTRATO
		Contrato contrato = new Contrato();
		contrato.setEntidadBancaria("Cajasol");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(1850.00);
		contrato.setPersonal(administradorService.findByNIF("18845878A"));
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		contratoService.save(contrato);
	}
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	
	@Test
	@Transactional
	@DisplayName("RN: Salario Minimo (update) -- caso positivo")
	void updateSaveContratoSalarioMinimoExceptionPositive() {
		
		Contrato contrato = contratoService.findAll().iterator().next();
		contrato.setNomina(1450.00);
		
		assertThat(contratoService.findAll().iterator().next().getNomina()).isEqualTo(1450.00);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Salario Minimo (new) -- caso positivo")
	void newSaveContratoSalarioMinimoExceptionPositive() {
		
		Contrato contrato = new Contrato();
		contrato.setEntidadBancaria("Cajasol");
		contrato.setFechaFin(LocalDate.of(2200, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2009, 5, 5));
		contrato.setNomina(1300.00);
		contrato.setPersonal(administradorService.findByNIF("18845878A"));
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		contrato = contratoService.save(contrato);
		
		assertThat(contratoService.findById(contrato.getId())).isEqualTo(contrato);
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Salario Minimo (update) -- caso negativo")
	void updateSaveContratoSalarioMinimoExceptionNegative() {
		
		Contrato contrato = contratoService.findAll().iterator().next();
		contrato.setNomina(450.0);
		
		assertThrows(SalarioMinimoException.class, () -> {contratoService.save(contrato);});
	}
	
	@Test
	@Transactional
	@DisplayName("RN: Salario Minimo (new) -- caso negativo")
	void newSaveContratoSalarioMinimoExceptionNegative() {
		
		Contrato contrato = new Contrato();
		contrato.setEntidadBancaria("Cajasol");
		contrato.setFechaFin(LocalDate.of(2200, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2009, 5, 5));
		contrato.setNomina(300.00);
		contrato.setPersonal(administradorService.findByNIF("18845878A"));
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		assertThrows(SalarioMinimoException.class, () -> {contratoService.save(contrato);});
		List<Contrato> lc = contratoService.findAll();
		assertThat(lc.get(lc.size()-1)).isNotEqualTo(contrato);
	}

}
