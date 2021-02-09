package org.springframework.gresur.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.ExceptionHandlerConfiguration;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = EstanteriaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class,ExceptionHandlerConfiguration.class})
class EstanteriaControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RolRepository roleRepository;
	
	@MockBean
	PasswordEncoder encoder;
	
	@MockBean
	EstanteriaService estanteriaService;
	
	@MockBean
	AlmacenService almacenService;

	//Datos a Testear
	private static final String CATEGORIA = "BANOS";

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET		ALL ESTANTERIAS														 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("GET All Estanterias")
	@Test
	void getAllEstanteriasIsOk() throws Exception{
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/estanterias")
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									UPDATE	 ESTANTERIA	CAPACIDAD												 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("UPDATE Estanteria Capacidad -- caso positivo")
	@Test
	void updateEstanteriasIsOk() throws Exception{
	
		//Devuelve Una Estanteria Para Cualquier Categoria
		given(this.estanteriaService.findByCategoria(any(Categoria.class))).willReturn(new Estanteria());
				
		//Peticion PUT
		mockMvc.perform(MockMvcRequestBuilders
				.put("/api/estanterias/update/{categoria}/{capacidad}/{version}", CATEGORIA, 20.0,0)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("UPDATE Estanteria Capacidad -- caso negativo")
	@Test
	void updateEstanteriasIsError() throws Exception{
	
		//Devuelve Una Estanteria Para Cualquier Categoria
		given(this.estanteriaService.findByCategoria(any(Categoria.class))).willReturn(null);
				
		//Peticion PUT
		String error = mockMvc.perform(MockMvcRequestBuilders
				.put("/api/estanterias/update/{categoria}/{capacidad}/{version}", CATEGORIA, 20.0,0)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();

		//Comprobamos Mensaje Error Es El Esperado
		assertThat(error).isEqualTo("Estanteria no encontrada");
	}
	
	
	
	
	
	

}
