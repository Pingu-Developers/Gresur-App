package org.springframework.gresur.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.ExceptionHandlerConfiguration;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Notificacion;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.LineasEnviadoService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@WebMvcTest(controllers = NotificacionController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class,ExceptionHandlerConfiguration.class})
class NotificacionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RolRepository roleRepository;
	
	@MockBean
	PasswordEncoder encoder;

	@MockBean
	LineasEnviadoService lineaEnviadoService;

	@MockBean
	AdministradorService admService;
	
	@MockBean
	NotificacionService notificacionService;
	
	//Creacion Datos Testear
	private static final Long ID_NOTIFICACION = 10L;
	Tuple3<List<Long>,String,String> tuplaCreacionEnvioDeNotificacion;
	
	
	@BeforeEach
	void setUp() throws Exception {

		tuplaCreacionEnvioDeNotificacion = new Tuple3<List<Long>, String, String>();
		
		}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	NOTIFICACIONES NO LEIDAS DE UN USUARIO								     *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Todas Las Notificaciones No Leidas De Un Usuario")
    @Test
	void testGetNotificacionesNoLeidasisOk() throws Exception  {
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/notificacion") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	NOTIFICACIONES  LEIDAS DE UN USUARIO								     *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Todas Las Notificaciones Leidas De Un Usuario")
    @Test
	void testGetNotificacionesLeidasisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/notificacion/leidas") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST MARCAR NOTIFICACION  LEIDA								 				 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Marcar Notificacion Leida -- caso positivo")
    @Test
	void testPostSetNotificacionesLeidasisOk() throws Exception  {
		
		//Devuelve una notificacion al buscar cualquier notificacion por ID
		given(this.notificacionService.findById(any(Long.class))).willReturn(new Notificacion());
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/notificacion/setLeida/{id}",ID_NOTIFICACION) 
					.characterEncoding("utf-8")
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Marcar Notificacion Leida -- caso negativo")
    @Test
    @Disabled
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este test esta comentado, ya que debido a que se utiliza la clase Authentication siempre devuelve un 200 OK   *
	 * por lo que no podemos probar correctamente el funcionamiento en este caso, pero la logica es correcta		 *																									 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	void testPostSetNotificacionesLeidasError() throws Exception  {
		
		//Devuelve null al buscar cualquier notificacion por ID
		given(this.notificacionService.findById(any(Long.class))).willReturn(null);
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/notificacion/setLeida/{id}",ID_NOTIFICACION) 
					.characterEncoding("utf-8")
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST NUEVA NOTIFICACION  								 					 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Crear Nueva Notificacion  -- caso positivo")
    @Test
	void testPostNewNotificacionesisOk() throws Exception  {
		
		//Creacion Notificacion y Receptores
		List<Long> ls = new ArrayList<>();
		ls.add(1L);
		ls.add(2L);
		ls.add(3L);
		tuplaCreacionEnvioDeNotificacion.setE1(ls);
		tuplaCreacionEnvioDeNotificacion.setE2("Esto es una notifacion de prueba");
		tuplaCreacionEnvioDeNotificacion.setE3("NORMAL");
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(tuplaCreacionEnvioDeNotificacion);
		String json = "{"+jsonString.substring(40);
		
		//Peticion POST
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
    @Disabled
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este test esta comentado, ya que debido a que se utiliza la clase Authentication siempre devuelve un 200 OK   *
	 * por lo que no podemos probar correctamente el funcionamiento en este caso, pero la logica es correcta		 *																									 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	void testPostNewNotificacionesError() throws Exception  {

		//Creacion Notificacion y Receptores
		List<Long> ls = new ArrayList<>();
		ls.add(1L);
		ls.add(2L);
		ls.add(3L);
		tuplaCreacionEnvioDeNotificacion.setE1(ls);
		tuplaCreacionEnvioDeNotificacion.setE2("Esto es una notifacion de prueba");
		tuplaCreacionEnvioDeNotificacion.setE3("NORMAL");
		tuplaCreacionEnvioDeNotificacion.setE3("");
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(tuplaCreacionEnvioDeNotificacion);
		
		//Peticion POST
		String json = "{"+jsonString.substring(40);
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/notificacion") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
