package org.springframework.gresur.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.EncargadoDeAlmacen;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ClienteService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
@WebMvcTest(controllers = ClienteController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ClienteService clienteService;
	
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
		cliente = new Cliente();
		cliente.setName("Manolo Vicente Alvarez Toledo");
		cliente.setEmail("manolito@gmail.com");
		cliente.setTlf("678675412");
		cliente.setNIF("20071214R");
		cliente.setDireccion("Calle Andalucia n13");
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 ALL CLIENTES								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Consultar clientes")
    @Test
	void testGetAllClientesisOk() throws Exception  {
	
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/cliente") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST    CLIENTE								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Agregar nuevo cliente -- caso positivo")
    @Test
	void testPostClienteisOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(cliente).replace("NIF", "nif");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/cliente/add") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Agregar nuevo cliente -- caso negativo")
    @Test
	void testPostClienteError() throws Exception  {
		cliente.setName("");
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(cliente).replace("NIF", "nif");
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/cliente/add") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET     CLIENTE	 DEFAULTER   BY NIF								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Consultar cliente defaulter por nif -- caso positivo")
    @Test
	void testGetClienteDefaulterByNifisOk() throws Exception  {
		given(this.clienteService.findByNIF(any(String.class))).willReturn(new Cliente());
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/cliente/{NIF}/isDefaulter",cliente.getNIF()) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Consultar cliente defaulter por nif -- caso negativo")
    @Test
	void testGetClienteDefaulterByNifError() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/cliente/{NIF}/isDefaulter",cliente.getNIF()) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET     CLIENTE	    BY NIF								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Consultar cliente  por nif -- caso positivo")
    @Test
	void testGetClienteByNifisOk() throws Exception  {
		given(this.clienteService.findByNIF(any(String.class))).willReturn(new Cliente());
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/cliente/{NIF}",cliente.getNIF()) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Consultar cliente  por nif -- caso negativo")
    @Test
	void testGetClienteByNifisError() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/cliente/{NIF}",cliente.getNIF()) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
