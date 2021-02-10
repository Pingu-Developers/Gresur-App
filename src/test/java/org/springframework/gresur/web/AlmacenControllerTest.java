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
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = AlmacenController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class AlmacenControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RolRepository roleRepository;
	
	@MockBean
	PasswordEncoder encoder;
	
	@MockBean
	AlmacenService almacenService;
	
	@MockBean
	ProductoService productoService;
	
	@MockBean
	EstanteriaService zonaService;
	
	//Datos a Testear
	private static final Long ID_ALMACEN = 2L;
	private Almacen almacen;
	
	@BeforeEach
	void setUp() throws Exception {
		
		almacen = new Almacen();
	
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET		GESTION																 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("GET Gestion Almacen")
	@Test
	void getGestionIsOk() throws Exception{
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/almacen/gestion")
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET		GESTION 	ENCARGADO												 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("GET Gestion Encargado")
	@Test
	void getGestionEncargadoIsOk() throws Exception{
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/almacen/gestionEncargado/{almacenAdm}",ID_ALMACEN)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET		CATEGORIAS															 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("GET Categorias")
	@Test
	void getCategoriasIsOk() throws Exception{
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/almacen/categorias/{almacenAdm}",ID_ALMACEN)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET		ALMACENES															 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("GET Almacenes")
	@Test
	void getAlmacenesIsOk() throws Exception{
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/almacen/")
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST	ADD	ALMACENES														*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Almacenes -- caso positivo")
	@Test
	void postAlmacenIsOk() throws Exception{
		
		//Creacion Almacen
		almacen.setCapacidad(2500.0);
		almacen.setDireccion("Poligono El Gastor - Cádiz");
		
		//Creacion de Objeto a JSON
		Gson gson = new Gson();
		String jsonString = gson.toJson(almacen);
		
		//Devuelve Almacen Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.almacenService.save(any(Almacen.class))).willReturn(new Almacen());
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/almacen")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Almacenes -- caso negativo")
	@Test
	void postAlmacenError() throws Exception{
		
		//Creacion Almacen
		almacen.setCapacidad(2500.0);
		almacen.setDireccion("");
		
		//Devuelve Almacen Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.almacenService.save(any(Almacen.class))).willReturn(new Almacen());
		
		//Creacion de Objeto a JSON
		Gson gson = new Gson();
		String jsonString = gson.toJson(almacen);
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/almacen")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}

}
