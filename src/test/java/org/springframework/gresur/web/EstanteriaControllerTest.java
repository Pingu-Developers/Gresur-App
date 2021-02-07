package org.springframework.gresur.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = EstanteriaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
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
	
	@Mock
	Estanteria estanteria;
	
	@Mock
	Almacen almacen;
	
	//private Estanteria est1;
	//private Estanteria est2;
	private Estanteria est;
	private Almacen alm;
	
	@BeforeEach
	void setUp() {
		alm = new Almacen();
		alm.setCapacidad(30000.0);
		alm.setDireccion("Los Algodonales");
		
		est = new Estanteria();
		est.setAlmacen(alm);
		est.setCapacidad(3000.0);
		est.setCategoria(Categoria.SILICES);
				
		/*est1 = new Estanteria();
		est1.setAlmacen(alm);
		est1.setCapacidad(3000.0);
		est1.setCategoria(Categoria.AZULEJOS);
				
		est2 = new Estanteria();
		est2.setAlmacen(alm);
		est2.setCapacidad(3000.0);
		est2.setCategoria(Categoria.LADRILLOS);*/
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET		ALL ESTANTERIAS														 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("GET All Estanterias")
	@Test
	void getAllEstanteriasIsOk() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/estanterias")
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									UPDATE	 ESTANTERIA	CAPACIDAD												*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("UPDATE Estanteria Capacidad")
	@Test
	void updateEstanteriasIsOk() throws Exception{
		given(this.estanteriaService.findByCategoria(any(Categoria.class))).willReturn(est);
		when(estanteria.getAlmacen()).thenReturn(new Almacen());
		mockMvc.perform(MockMvcRequestBuilders
				.put("/api/estanterias/update/{categoria}/{capacidad}", est.getCategoria(), 20.0)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	
	
	
	
	

}
