package org.springframework.gresur.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.ExceptionHandlerConfiguration;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoNotificacion;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.LineasEnviadoService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;

@WebMvcTest(controllers = NotificacionController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class,ExceptionHandlerConfiguration.class})
class NotificacionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	UserDetailsService userDetails;

	@MockBean
	LineasEnviadoService lineaEnviadoService;

	@MockBean
	AdministradorService admService;
	
	@MockBean
	NotificacionService notificacionService;
	
	@Mock
	Notificacion notificacion;
	
	private Long idNotificacion = 10L;
	
	Tuple3<List<Long>,String,String> t3;
	
	
	@BeforeEach
	void setUp() throws Exception {

		t3 = new Tuple3<List<Long>, String, String>();
		List<Long> ls = new ArrayList<>();
		ls.add(1L);
		ls.add(2L);
		ls.add(3L);
		t3.setE1(ls);
		t3.setE2("Esto es una notifacion de prueba");
		t3.setE3("NORMAL");
		
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	NOTIFICACIONES NO LEIDAS DE UN USUARIO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Todas Las Notificaciones No Leidas De Un Usuario")
    @Test
	void testGetNotificacionesNoLeidasisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/notificacion") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	NOTIFICACIONES  LEIDAS DE UN USUARIO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Todas Las Notificaciones Leidas De Un Usuario")
    @Test
	void testGetNotificacionesLeidasisOk() throws Exception  {
		
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/notificacion/leidas") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST MARCAR NOTIFICACION  LEIDA								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Marcar Notificacion Leida -- caso positivo")
    @Test
	void testPostSetNotificacionesLeidasisOk() throws Exception  {
		given(this.notificacionService.findById(any(Long.class))).willReturn(new Notificacion());
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/notificacion/setLeida/{id}",idNotificacion) 
					.characterEncoding("utf-8")
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Marcar Notificacion Leida -- caso negativo")
    @Test
	void testPostSetNotificacionesLeidasError() throws Exception  {
		given(this.notificacionService.findById(any(Long.class))).willReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/notificacion/setLeida/{id}",idNotificacion) 
					.characterEncoding("utf-8")
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST NUEVA NOTIFICACION  								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Crear Nueva Notificacion  -- caso positivo")
    @Test
	void testPostNewNotificacionesisOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(t3);
		String json = "{"+jsonString.substring(40);
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/notificacion") 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Crear Nueva Notificacion  -- caso negativo")
    @Test
	void testPostNewNotificacionesError() throws Exception  {
		t3.setE3("");
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(t3);
		String json = "{"+jsonString.substring(40);
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/notificacion") 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
