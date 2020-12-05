package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class ContratoServiceTests {
	@Autowired
	protected AdministradorService administradorService;
	
	@Autowired
	protected ContratoService contratoService;
	
	@Autowired
	protected ConfiguracionService confService;
	
	@BeforeEach
	@Transactional
	void initAll() {
		Configuracion conf = new Configuracion();
		conf.setSalarioMinimo(900.00);
		conf.setNumMaxNotificaciones(100);
		
		confService.updateConfig(conf);
		
		Administrador adm = new Administrador();
		adm.setName("Jose Luis Gresur");
		adm.setNIF("18845878A");
		adm.setEmail("e1@email.com");
		adm.setTlf("696823445");
		adm.setDireccion("Av. Reina Mercedes");
		adm.setNSS("12 1234123890");
		adm.setImage("/resources/foto.png");
		
		administradorService.save(adm);
		
		Contrato contrato = new Contrato();
		contrato.setEntidadBancaria("Cajasol");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(1850.00);
		contrato.setPersonal(administradorService.findByNIF("18845878A"));
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		contratoService.save(contrato);
	}
	
	@AfterEach
	@Transactional
	void clearAll() {
		confService.deleteAll();
		administradorService.deleteAll();
		contratoService.deleteAll();
	}

	//Tests
	
	@CsvSource({
		"Cajasol"
	})
	@ParameterizedTest
	@Transactional
	void findContratoById(String entidadBancaria) {
		Long id = contratoService.findAll().iterator().next().getId();
		Contrato contrato = contratoService.findById(id);
		assertThat(contrato.getEntidadBancaria()).isEqualTo(entidadBancaria);
	}
	
	@Test
	@Transactional	
	void deleteContratoById() {
		Long id = contratoService.findAll().iterator().next().getId();
		contratoService.deleteById(id);
		assertThat(contratoService.count()).isEqualTo(0);
	}
	
	@CsvSource({
		"450.00"
	})
	@ParameterizedTest
	@Transactional
	void saveContratoSalarioMinimoException(Double nomina) {
		Contrato contrato = contratoService.findAll().iterator().next();
		contrato.setNomina(nomina);
		RuntimeException exception = assertThrows(SalarioMinimoException.class, () -> {contratoService.save(contrato);});
		
		String expectedMessage = "El salario es menor que el salario minimo";
		String actualMessage = exception.getMessage();
		
		System.out.println("HOLA. " + contratoService.findAll().iterator().next().getNomina());
		
		assertThat(expectedMessage).contains(actualMessage);
	}

}
