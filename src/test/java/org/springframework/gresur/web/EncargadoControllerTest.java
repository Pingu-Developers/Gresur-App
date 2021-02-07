package org.springframework.gresur.web;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(controllers = EncargadoDeAlmacenController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

class EncargadoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EncargadoDeAlmacenService encargadoService;
	
	@MockBean
	private AlmacenService almacenService;
	

	@BeforeEach
	void setUp() throws Exception {

	}
	
	@WithMockUser(value = "spring")
    @Test
	void testGetAlmacenesDisponibles() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.		
					get("/api/encargado/almacen")
					).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
