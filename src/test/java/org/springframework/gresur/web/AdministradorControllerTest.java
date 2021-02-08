package org.springframework.gresur.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.ExceptionHandlerConfiguration;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;

@WebMvcTest(controllers = AdministradorController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class,ExceptionHandlerConfiguration.class})

class AdministradorControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RolRepository roleRepository;
	
	@MockBean
	PasswordEncoder encoder;
	
	@MockBean
	EncargadoDeAlmacenService encargadoService;
	
	@MockBean
	DependienteService dependienteService;
	
	@MockBean
	TransportistaService transportistaService;
	
	@MockBean
	AdministradorService admService;
	
	private Administrador administrador;
	private Dependiente dependiente;
	private EncargadoDeAlmacen encargado;
	private Transportista transportista;
	private Personal personal;
	
	@BeforeEach
	void setUp() throws Exception {
		administrador = new Administrador();
		administrador.setName("Paco Manuel Lopez");
		administrador.setDireccion("Calle Larios");
		administrador.setEmail("paco@gmail.com");
		administrador.setNIF("00070284X");
		administrador.setNSS("123456789963");
		administrador.setTlf("678675482");
		
		dependiente = new Dependiente();
		dependiente.setName("Jose Manuel Lopez");
		dependiente.setDireccion("Calle Trigrida");
		dependiente.setEmail("jose@gmail.com");
		dependiente.setNIF("10070284X");
		dependiente.setNSS("123456789964");
		dependiente.setTlf("478675482");
		
		encargado = new EncargadoDeAlmacen();
		encargado.setName("Lucas Manuel Lopez");
		encargado.setDireccion("Calle Leon");
		encargado.setEmail("lucas@gmail.com");
		encargado.setNIF("20070284X");
		encargado.setNSS("123456789965");
		encargado.setTlf("378675482");
		Almacen alm = new Almacen();
		alm.setCapacidad(30.0);
		alm.setDireccion("Los Algodonales");
		encargado.setAlmacen(alm);
		
		transportista = new Transportista();
		transportista.setName("Antonio Manuel Lopez");
		transportista.setDireccion("Calle Andalucia");
		transportista.setEmail("antonio@gmail.com");
		transportista.setNIF("30070284X");
		transportista.setNSS("123456789966");
		transportista.setTlf("278675482");
		
		personal = new Personal();
		personal.setName("Jose Luis Sierra");
		personal.setDireccion("Calle Recreativo de Huelva");
		personal.setEmail("oceluis@gmail.com");
		personal.setNIF("80070284X");
		personal.setNSS("123451780966");
		personal.setTlf("978175382");
		

	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST	ADMINISTRADOR								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Añadir Administrador -- caso positivo")
    @Test
	void testPostAdministradorisOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(administrador).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/adm/add/administrador") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Añadir Administrador -- caso negativo")
    @Test
	void testPostAdministradorError() throws Exception  {
		administrador.setName("");
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(administrador).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/adm/add/administrador") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										POST	DEPENDIENTE							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Añadir Dependiente -- caso positivo")
    @Test
	void testPostDependienteisOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(dependiente).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/adm/add/dependiente") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Añadir Dependiente -- caso negativo")
    @Test
	void testPostDependienteError() throws Exception  {
		dependiente.setName("");
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(dependiente).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/adm/add/dependiente") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										POST	TRANSPORTISTA							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Añadir Transportista -- caso positivo")
    @Test
	void testPostTransportistaisOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(transportista).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/adm/add/transportista") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Añadir Transportista -- caso negativo")
    @Test
	void testPostTransportistaError() throws Exception  {
		transportista.setName("");
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(transportista).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/adm/add/transportista") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										POST	ENCARGADO							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Añadir Encargado -- caso positivo")
    @Test
	void testPostEncargadoisOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(encargado).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/adm/add/encargado") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Añadir Encargado -- caso negativo")
    @Test
	void testPostEncargadoError() throws Exception  {
		encargado.setName("");
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(encargado).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/adm/add/encargado") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										GET	PERSONAL							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Mostrar todo el personal")
    @Test
	void testGetPersonalisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/adm/personal") 
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										GET	PERSONAL POR NIF							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Mostrar el personal por nif  -- caso positivo")
    @Test
	void testGetPersonalByNIFisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/adm/personal/{dni}",dependiente.getNIF())
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Mostrar el personal por nif  -- caso negativo")
    @Test
	void testGetPersonalByNIFError() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/adm/personal/{dni}","20070284RR")
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										GET	PERSONAL PERFIL							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Mostrar el perfil del personal")
    @Test
	void testGetPersonalProfileisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/adm/personal/profile")
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										PUT	PERSONAL PERFIL							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Editar perfil del personal  -- caso positivo")
    @Test
	void testPutPersonalProfileisOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(personal).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					put("/api/adm/personal/profile")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Editar perfil del personal  -- caso negativo")
    @Test
	void testPutPersonalProfileError() throws Exception  {
		personal.setDireccion("");
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(personal).replace("NIF", "nif").replace("NSS", "nss");
		mockMvc.perform(MockMvcRequestBuilders.
					put("/api/adm/personal/profile")
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
