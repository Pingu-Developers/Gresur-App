package org.springframework.gresur.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class NotificacionServiceTest {
	
	@Autowired 
	protected NotificacionService notificacionService;
	
	@Autowired
	protected AdministradorService administradorService;
	
	@Test
	@Transactional
	void AddTest() {
		Administrador adm = new Administrador();
		adm.setDireccion("asd");
		adm.setEmail("asd@gmail.com");
		adm.setImage("asd");
		adm.setName("Kknzskn");
		adm.setNIF("12345678Q");
		adm.setNSS("123456789001");
		adm.setTlf("123456789");
		administradorService.add(adm);
		adm = administradorService.findByNIF("12345678Q");
		
		Notificacion not = new Notificacion();
		not.setEmisor(adm);
		not.setLeido(false);
		not.setTipoNotificacion(TipoNotificacion.NORMAL);
		List<Personal> la = new ArrayList<Personal>();
		la.add(adm);
		not.setReceptores(la);
		notificacionService.add(not);
		
		System.out.println("aaaaa");
		assertTrue(notificacionService.findAll().iterator().next().getEmisor().equals(adm));
	}

}
