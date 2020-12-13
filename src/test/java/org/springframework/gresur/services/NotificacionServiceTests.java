package org.springframework.gresur.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDateTime;
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
import org.springframework.gresur.model.Configuracion;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.repository.ConfiguracionRepository;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.service.PersonalService;
import org.springframework.gresur.service.exceptions.NotificacionesLimitException;
import org.springframework.gresur.util.DBUtility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class NotificacionServiceTests {

	@Autowired
	protected NotificacionService notificacionService;
	
	@Autowired
	protected ConfiguracionService configuracionService;
	
	@Autowired
	protected ConfiguracionRepository configuracionR;
	
	@Autowired
	protected PersonalService<Transportista, ?> personalService;
	
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
		
		//Datos del config
		Configuracion config = new Configuracion();
		config.setNumMaxNotificaciones(1);
		config.setSalarioMinimo(950.);
		configuracionService.updateConfig(config);
		
		
		//Creamos personal que se envía y recibe la notificacion
		Transportista emisor = new Transportista();
		emisor.setName("Paco Pepe");
		emisor.setDireccion("Calle La Mancha Nº7");
		emisor.setEmail("pacopepe@gmail.com");
		emisor.setImage("docs/imgs/pacopepepic.png");
		emisor.setNIF("11170284R");
		emisor.setNSS("12 1234123890");
		emisor.setTlf("978758520");
		
		Transportista receptor = new Transportista();
		receptor.setName("Paco Manuel");
		receptor.setDireccion("Calle La Mina Nº7");
		receptor.setEmail("pacomanu@gmail.com");
		receptor.setImage("docs/imgs/pacomanupic.png");
		receptor.setNIF("12170284R");
		receptor.setNSS("12 1237123890");
		receptor.setTlf("678758020");
		
		Transportista receptor2 = new Transportista();
		receptor2.setName("Paco Jose");
		receptor2.setDireccion("Calle La Palometa Nº7");
		receptor2.setEmail("pacoce@gmail.com");
		receptor2.setImage("docs/imgs/pacojosepic.png");
		receptor2.setNIF("12240284R");
		receptor2.setNSS("12 1337123890");
		receptor2.setTlf("678709020");
				
		personalService.save(emisor);
		personalService.save(receptor);
		personalService.save(receptor2);
		
		//Creamos la notificacion
		Notificacion noc1 = new Notificacion();
		noc1.setCuerpo("Pepe y Jose a ver si podeis ayudarme esta tarde a cambiarle el aceite al camion grua");
		noc1.setTipoNotificacion(TipoNotificacion.NORMAL);
		noc1.setFechaHora(LocalDateTime.of(2020, 12, 13, 17, 30));
		noc1.setEmisor(emisor);
		List<Personal> receptores = new ArrayList<Personal>();
		receptores.add(receptor);
		receptores.add(receptor2);
		
		notificacionService.save(noc1, receptores);
	}

	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
* 										FUNCIONES DE LOS TESTS													 *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
	/* * * * * * * * * * * * *
	 *   FIND-REMOVE TESTS   *
	 * * * * * * * * * * * * */
	@Test
	@Transactional
	@DisplayName("findAllNotificacionesByEmisor -- Caso Positivo")
	void findAllNotificacionesByEmisor() {
		List<Notificacion> noc = notificacionService.findAllNotificacionesByEmisorName("Paco Pepe");
		assertThat(noc.size()).isEqualTo(1);
	}
	@Test
	@Transactional
	@DisplayName("findAllNotificacionesByEmisorNotFound -- Caso Negativo")
	void findAllNotificacionesByEmisorNotFound() {
		List<Notificacion> noc = notificacionService.findAllNotificacionesByEmisorName("Paco Manuel");
		assertThat(noc.size()).isEqualTo(0);
	}
	
	@Test
	@Transactional
	@DisplayName("findNoLeidasPersonal -- Caso Positivo")
	void findNoLeidasPersonal() {
		Personal p = personalService.findByNIF("12170284R");
		List<Notificacion> noc = notificacionService.findNoLeidasPersonal(p);
		assertThat(noc.size()).isEqualTo(1);
	}
		
	@Test
	@Transactional
	@DisplayName("findNoLeidasPersonal -- Caso Negativo")
	void findNoLeidasPersonalNotFound() {
		Personal p = personalService.findByNIF("11170284R");
		List<Notificacion> noc = notificacionService.findNoLeidasPersonal(p);
		assertThat(noc.size()).isEqualTo(0);
	}
	
	
	/* * * * * * * * * * * * * * * *
	 *   REGLAS DE NEGOCIO TESTS   *
	 * * * * * * * * * * * * * * * */
	@Test
	@Transactional
	@DisplayName("RN: Limite de notificaciones (NotificacionesLimitException)")
	void saveNotificacionesLimitExceded() {
		
		// Creamos una notificacion para comprobar que se excede el limite de notificaciones
		
		Personal emisor = personalService.findByNIF("11170284R");
		Personal receptor = personalService.findByNIF("12170284R");
		Personal receptor2 = personalService.findByNIF("12240284R");

		Notificacion noc = new Notificacion();
		noc.setCuerpo("Cena de empresa hoy ¿quien se apunta?");
		noc.setTipoNotificacion(TipoNotificacion.NORMAL);
		noc.setFechaHora(LocalDateTime.of(2020, 12, 13, 18, 30));
		noc.setEmisor(emisor);
		List<Personal> receptores = new ArrayList<Personal>();
		receptores.add(receptor);
		receptores.add(receptor2);
		notificacionService.save(noc,receptores);

		assertThrows(NotificacionesLimitException.class, ()->{notificacionService.save(noc,receptores);});
		
	}

	
	
}
