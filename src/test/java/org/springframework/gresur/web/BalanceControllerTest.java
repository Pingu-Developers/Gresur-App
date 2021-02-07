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
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = BalanceController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)


class BalanceControllerTest {

	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	FacturaEmitidaService facturaEmitidaService;
	
	@MockBean
	FacturaRecibidaService facturaRecibidaService;
	
	private Integer year;
	
	@BeforeEach
	void setUp() throws Exception {
		year = 2018;
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	BALANCE								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Consultar Balance Por Año -- caso positivo")
    @Test
	void testGetBalanceByYearisOk() throws Exception  {
		
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/balance/{year}",year) 
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Consultar Balance Por Año -- caso negativo")
    @Test
	void testGetBalanceByYearError() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/balance/{year}","") 
					.contentType(MediaType.APPLICATION_JSON)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
