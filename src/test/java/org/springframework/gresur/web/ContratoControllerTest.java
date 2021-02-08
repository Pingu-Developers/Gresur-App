package org.springframework.gresur.web;

import org.apache.jasper.tagplugins.jstl.core.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Administrador;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoJornada;
import org.springframework.gresur.repository.PersonalRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ContratoService;
import org.springframework.gresur.service.PersonalService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import com.google.gson.Gson;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = ContratoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ContratoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ContratoService contratoService;
	
	@MockBean
	AdministradorService admService;
	
	private String rolTodos = "TODOS";
	private String rol = "DEPENDIENTE";
	private String nif = "20070284E";
	private Contrato contrato;
	
	@BeforeEach
	void setUp() throws Exception {

		contrato = new Contrato();
		contrato.setEntidadBancaria("CaixaBank");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(2700.);
		contrato.setObservaciones("Todo Ok Jose Luis");
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		contrato.setPersonal(null);

	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 CONTRATO DEL PERSONAL							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Contrato Del Todo El Personal")
    @Test
	void testGetAllContratoPersonalisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/contrato/{rol}",rolTodos) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Contrato De Un Rol")
    @Test
	void testGetAllContratoPersonalByRolisOk() throws Exception  {
		
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/contrato/{rol}",rol) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST AÑADIR CONTRATO POR NIF							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Agregar Contrato Por NIF -- caso positivo")
    @Test
	void testPostAddContratoByNifisOk() throws Exception  {
		nif = "dnfsodnfiosdn";
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(contrato);
		
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/contrato/add/{nif}", nif)
					.with(csrf())
					.characterEncoding("utf-8")
					.content(jsonString)
					.contentType(MediaType.APPLICATION_JSON)
					).andExpect(MockMvcResultMatchers.status().isBadRequest());		
	}
	
		/*		@WithMockUser(value = "spring")
	@DisplayName("Agregar Contrato Por NIF -- caso negativo")
    @Test
	void testPostAddContratoByNifError() throws Exception  {
		nif = "20070284EE";
		when(this.admService.findByNIF(any(String.class))).thenReturn(null);
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(contrato);
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/contrato/add/{nif}",nif) 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
*/
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT ACTUALIZAR CONTRATO POR NIF							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/*	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Contrato Por NIF -- caso positivo")
    @Test
	void testPutActualizarContratoByNifisOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(contrato);
		mockMvc.perform(MockMvcRequestBuilders.
					put("/api/contrato/update/23") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
		@WithMockUser(value = "spring")
	@DisplayName("Actualizar Contrato Por NIF -- caso negativo")
    @Test
    @Disabled
	void testPutActualizarContratoByNifError() throws Exception  {
		
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(contrato);
		mockMvc.perform(MockMvcRequestBuilders.
					put("/api/contrato/update/{nif}",nif) 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	*/
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									DELETE  CONTRATO POR NIF							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Borrar Contrato Por NIF -- caso positivo")
    @Test
	void testDeleteContratoByNifisOk() throws Exception  {
		given(this.admService.findByNIFPersonal(any(String.class))).willReturn(new Personal());
		mockMvc.perform(MockMvcRequestBuilders.
					delete("/api/contrato/delete/{nif}",nif) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Borrar Contrato Por NIF -- caso negativo(No existe empleado con NIF)")
    @Test
	void testDeleteContratoByNifError() throws Exception  {
	    nif="20070277X";
		String error = mockMvc.perform(MockMvcRequestBuilders.
				delete("/api/contrato/delete/{nif}",nif) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn().getResponse().getContentAsString();
		assertThat(error).isEqualTo("No existe ningun empleado con dicho NIF");
	}
	@WithMockUser(value = "spring")
	@DisplayName("Borrar Contrato Por NIF -- caso negativo(Formato NIF No Valido)")
    @Test
	void testDeleteContratoByNifErrorNifInvalid() throws Exception  {
	    nif="20070277XEE";
		String error = mockMvc.perform(MockMvcRequestBuilders.
				delete("/api/contrato/delete/{nif}",nif) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn().getResponse().getContentAsString();
		assertThat(error).isEqualTo("Formato del NIF invalido");
	}
	



}
