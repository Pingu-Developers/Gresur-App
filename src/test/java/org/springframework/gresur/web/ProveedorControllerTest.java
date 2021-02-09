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
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = ProveedorController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class,ExceptionHandlerConfiguration.class})
class ProveedorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProveedorService proveedorService;
	
	private Proveedor proveedor;
	
	@BeforeEach
	void setUp() throws Exception {
		
		proveedor = new Proveedor();

	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										GET	PROVEEDORES							                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Mostrar todos los proveedores")
    @Test
	void testGetAllProveedoresisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/proveedor") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}


	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 										POST	PROVEEDOR							                             *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Agregar un proveedor -- caso positivo")
	@Test
	void testPostProveedorisOk() throws Exception  {
		
		//Devuelve Proveedor Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.proveedorService.save(any(Proveedor.class))).willReturn(new Proveedor());
		
		//Creacion Proveedor
		proveedor.setName("Paco Antonio Bermudez Sanchez");
		proveedor.setDireccion("Calle Nostradammus");
		proveedor.setEmail("pacoant@gmail.com");
		proveedor.setTlf("678698123");
		proveedor.setNIF("20070211X");
		proveedor.setIBAN("ES2114640100722030876293");
		
		//Creacion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(proveedor).replace("NIF", "nif").replace("IBAN", "iban");
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/proveedor") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Agregar un proveedor -- caso negativo")
	@Test
	void testPostProveedorError() throws Exception  {
		
		//Creacion Proveedor
		proveedor.setName("Paco Antonio Bermudez Sanchez");
		proveedor.setDireccion("Calle Nostradammus");
		proveedor.setEmail("pacoant@gmail.com");
		proveedor.setTlf("678698123");
		proveedor.setNIF("20070211X");
		proveedor.setIBAN("");
		
		//Creacion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(proveedor).replace("NIF", "nif").replace("IBAN", "iban");
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/proveedor") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}


}
	

