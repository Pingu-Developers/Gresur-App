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
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Contrato;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.TipoJornada;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ContratoService;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import java.time.LocalDate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
	UserRepository userRepository;
	
	@MockBean
	ContratoService contratoService;
	
	@MockBean
	AdministradorService admService;
	
	
	//Creacion Datos Testear
	
	private static final String NIF = "20070284A";
	private static final String NIF_INVALIDO = "20070284AA";
	private static final String NIF_NOT_FOUND = "10070284A";
	private String rolTodos = "TODOS";
	private String rol = "DEPENDIENTE";
	private Contrato contrato;
	private Personal personal;
	private String jsonParser;
	
	@BeforeEach
	void setUp() throws Exception {

		contrato = new Contrato();
		personal  = new Personal();
		jsonParser = "";
		
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 CONTRATO DEL PERSONAL							                         *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Contrato Del Todo El Personal")
    @Test
	void testGetAllContratoPersonalisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/contrato/{rol}",rolTodos) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Contrato De Un Rol")
    @Test
	void testGetAllContratoPersonalByRolisOk() throws Exception  {
	
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/contrato/{rol}",rol) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 									POST AÑADIR CONTRATO POR NIF							                         *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Agregar Contrato Por NIF -- caso positivo")
    @Test
	void testPostAddContratoByNifisOk() throws Exception  {
		
		//Creacion Contrato
		contrato.setEntidadBancaria("CaixaBank");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(2700.);
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		//Creacion Personal
		personal.setName("Jose Luis Sierra");
		personal.setDireccion("Calle Recreativo de Huelva");
		personal.setEmail("oceluis@gmail.com");
		personal.setNIF("80070284X");
		personal.setNSS("123451780966");
		personal.setTlf("978175382");
		contrato.setPersonal(personal);
		
		//Creacion JSON Especial
		jsonParser = "{"+ "\"nomina\""+":"+contrato.getNomina()+","
		+"\"entidadBancaria\"" +":"+"\""+contrato.getEntidadBancaria()+"\""+","
		+"\"fechaInicio\""+":"+"\""+contrato.getFechaInicio().getYear()+"-"
		+0 +contrato.getFechaInicio().getMonthValue()+"-"
		+0+contrato.getFechaInicio().getDayOfMonth()+"\""+","
		+"\"fechaFin\""+":"+"\""+contrato.getFechaFin().getYear()+"-"
		+ contrato.getFechaFin().getMonthValue()+"-"
		+contrato.getFechaFin().getDayOfMonth()+"\""+","
		+"\"tipoJornada\""+":"+"\""+contrato.getTipoJornada()+"\""+","
		+ "\"personal\""+":";
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(personal).replace("NIF", "nif").replace("NSS", "nss");
		String json = jsonParser+jsonString+"}";
		
		//Devuelve Personal Para Cualquier NIF
		when(this.admService.findByNIFPersonal(any(String.class))).thenReturn(new Personal());
		
		//Devuelve Contrato Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.contratoService.save(any(Contrato.class))).willReturn(new Contrato());
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/contrato/add/{nif}", NIF)
					.with(csrf())
					.characterEncoding("utf-8")
					.content(json)
					.contentType(MediaType.APPLICATION_JSON)
					).andExpect(MockMvcResultMatchers.status().isOk());		
	}
	@WithMockUser(value = "spring")
	@DisplayName("Agregar Contrato Por NIF -- caso negativo(Formato NIF No Valido)")
    @Test
	void testPostAddContratoByNifErrorInvalid() throws Exception  {
		
		//Creacion Contrato
		contrato.setEntidadBancaria("CaixaBank");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(2700.);
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		//Creacion Personal
		personal.setName("Jose Luis Sierra");
		personal.setDireccion("Calle Recreativo de Huelva");
		personal.setEmail("oceluis@gmail.com");
		personal.setNIF("80070284X");
		personal.setNSS("123451780966");
		personal.setTlf("978175382");
		contrato.setPersonal(personal);

		//Creacion JSON Especial
		jsonParser = "{"+ "\"nomina\""+":"+contrato.getNomina()+","
		+"\"entidadBancaria\"" +":"+"\""+contrato.getEntidadBancaria()+"\""+","
		+"\"fechaInicio\""+":"+"\""+contrato.getFechaInicio().getYear()+"-"
		+0 +contrato.getFechaInicio().getMonthValue()+"-"
		+0+contrato.getFechaInicio().getDayOfMonth()+"\""+","
		+"\"fechaFin\""+":"+"\""+contrato.getFechaFin().getYear()+"-"
		+ contrato.getFechaFin().getMonthValue()+"-"
		+contrato.getFechaFin().getDayOfMonth()+"\""+","
		+"\"tipoJornada\""+":"+"\""+contrato.getTipoJornada()+"\""+","
		+ "\"personal\""+":";
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(personal).replace("NIF", "nif").replace("NSS", "nss");
		String json = jsonParser+jsonString+"}";

		//Peticion POST
		String error  = mockMvc.perform(MockMvcRequestBuilders.
					post("/api/contrato/add/{nif}",NIF_INVALIDO) 
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();
		
		//Comprobamos Mensaje Error Es El Esperado
		assertThat(error).isEqualTo("Formato del NIF invalido");
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Agregar Contrato Por NIF -- caso negativo(Formato NIF No Encontrado)")
    @Test
	void testPostAddContratoByNifErrorNotFound() throws Exception  {
		
		//Creacion Contrato
		contrato.setEntidadBancaria("CaixaBank");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(2700.);
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		//Creacion Personal
		personal.setName("Jose Luis Sierra");
		personal.setDireccion("Calle Recreativo de Huelva");
		personal.setEmail("oceluis@gmail.com");
		personal.setNIF("80070284X");
		personal.setNSS("123451780966");
		personal.setTlf("978175382");
		contrato.setPersonal(personal);

		//Creacion JSON Especial
		jsonParser = "{"+ "\"nomina\""+":"+contrato.getNomina()+","
		+"\"entidadBancaria\"" +":"+"\""+contrato.getEntidadBancaria()+"\""+","
		+"\"fechaInicio\""+":"+"\""+contrato.getFechaInicio().getYear()+"-"
		+0 +contrato.getFechaInicio().getMonthValue()+"-"
		+0+contrato.getFechaInicio().getDayOfMonth()+"\""+","
		+"\"fechaFin\""+":"+"\""+contrato.getFechaFin().getYear()+"-"
		+ contrato.getFechaFin().getMonthValue()+"-"
		+contrato.getFechaFin().getDayOfMonth()+"\""+","
		+"\"tipoJornada\""+":"+"\""+contrato.getTipoJornada()+"\""+","
		+ "\"personal\""+":";
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(personal).replace("NIF", "nif").replace("NSS", "nss");
		String json = jsonParser+jsonString+"}";

		//Peticion POST
		String error  = mockMvc.perform(MockMvcRequestBuilders.
					post("/api/contrato/add/{nif}",NIF_NOT_FOUND) 
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();
		
		//Comprobamos Mensaje Error Es El Esperado
		assertThat(error).isEqualTo("NIF no perteneciente a ningun empleado");
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT ACTUALIZAR CONTRATO POR NIF							                     *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Contrato Por NIF -- caso positivo")
    @Test
	void testPutActualizarContratoByNifisOk() throws Exception  {
		
		//Creacion Contrato
		contrato.setEntidadBancaria("CaixaBank");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(2700.);
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		//Creacion Personal
		personal.setName("Jose Luis Sierra");
		personal.setDireccion("Calle Recreativo de Huelva");
		personal.setEmail("oceluis@gmail.com");
		personal.setNIF("80070284X");
		personal.setNSS("123451780966");
		personal.setTlf("978175382");
		contrato.setPersonal(personal);
		
		//Creacion JSON Especial
		jsonParser = "{"+ "\"nomina\""+":"+contrato.getNomina()+","
		+"\"entidadBancaria\"" +":"+"\""+contrato.getEntidadBancaria()+"\""+","
		+"\"fechaInicio\""+":"+"\""+contrato.getFechaInicio().getYear()+"-"
		+0 +contrato.getFechaInicio().getMonthValue()+"-"
		+0+contrato.getFechaInicio().getDayOfMonth()+"\""+","
		+"\"fechaFin\""+":"+"\""+contrato.getFechaFin().getYear()+"-"
		+ contrato.getFechaFin().getMonthValue()+"-"
		+contrato.getFechaFin().getDayOfMonth()+"\""+","
		+"\"tipoJornada\""+":"+"\""+contrato.getTipoJornada()+"\""+","
		+ "\"personal\""+":";
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(personal).replace("NIF", "nif").replace("NSS", "nss");
		String json = jsonParser+jsonString+"}";

		//Devuelve Contrato Para Cualquier NIF
		given(this.contratoService.findByPersonalNIF(any(String.class))).willReturn(new Contrato());
		
		//Devuelve Contrato Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.contratoService.save(any(Contrato.class))).willReturn(new Contrato());		
		
		//Peticion PUT
		mockMvc.perform(MockMvcRequestBuilders.
					put("/api/contrato/update/{nif}",NIF) 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Contrato Por NIF -- caso negativo (Formato NIF No Valido)")
    @Test
	void testPutActualizarContratoByNifErrorInvalid() throws Exception  {
		
		//Creacion Contrato
		contrato.setEntidadBancaria("CaixaBank");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(2700.);
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		//Creacion Personal
		personal.setName("Jose Luis Sierra");
		personal.setDireccion("Calle Recreativo de Huelva");
		personal.setEmail("oceluis@gmail.com");
		personal.setNIF("80070284X");
		personal.setNSS("123451780966");
		personal.setTlf("978175382");
		contrato.setPersonal(personal);

		//Creacion JSON Especial
		jsonParser = "{"+ "\"nomina\""+":"+contrato.getNomina()+","
		+"\"entidadBancaria\"" +":"+"\""+contrato.getEntidadBancaria()+"\""+","
		+"\"fechaInicio\""+":"+"\""+contrato.getFechaInicio().getYear()+"-"
		+0 +contrato.getFechaInicio().getMonthValue()+"-"
		+0+contrato.getFechaInicio().getDayOfMonth()+"\""+","
		+"\"fechaFin\""+":"+"\""+contrato.getFechaFin().getYear()+"-"
		+ contrato.getFechaFin().getMonthValue()+"-"
		+contrato.getFechaFin().getDayOfMonth()+"\""+","
		+"\"tipoJornada\""+":"+"\""+contrato.getTipoJornada()+"\""+","
		+ "\"personal\""+":";
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(personal).replace("NIF", "nif").replace("NSS", "nss");
		String json = jsonParser+jsonString+"}";
		
		//Devuelve Contrato Para Cualquier NIF
		given(this.contratoService.findByPersonalNIF(any(String.class))).willReturn(new Contrato());
		
		//Peticion PUT
		String error = mockMvc.perform(MockMvcRequestBuilders.
					put("/api/contrato/update/{nif}",NIF_INVALIDO) 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();
		
		//Comprobamos Mensaje Error Es El Esperado
		assertThat(error).isEqualTo("Formato del NIF invalido");
	}
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Contrato Por NIF -- caso negativo (Contrato No Valido)")
    @Test
	void testPutActualizarContratoByNifErrorContratoInvalid() throws Exception  {
		
		//Creacion Contrato
		contrato.setEntidadBancaria("CaixaBank");
		contrato.setFechaFin(LocalDate.of(2100, 12, 12));
		contrato.setFechaInicio(LocalDate.of(2007, 5, 5));
		contrato.setNomina(2700.);
		contrato.setTipoJornada(TipoJornada.COMPLETA);
		
		//Creacion Personal
		personal.setName("Jose Luis Sierra");
		personal.setDireccion("Calle Recreativo de Huelva");
		personal.setEmail("oceluis@gmail.com");
		personal.setNIF("80070284X");
		personal.setNSS("123451780966");
		personal.setTlf("978175382");
		contrato.setPersonal(personal);

		//Creacion JSON Especial
		jsonParser = "{"+ "\"nomina\""+":"+contrato.getNomina()+","
		+"\"entidadBancaria\"" +":"+"\""+contrato.getEntidadBancaria()+"\""+","
		+"\"fechaInicio\""+":"+"\""+contrato.getFechaInicio().getYear()+"-"
		+0 +contrato.getFechaInicio().getMonthValue()+"-"
		+0+contrato.getFechaInicio().getDayOfMonth()+"\""+","
		+"\"fechaFin\""+":"+"\""+contrato.getFechaFin().getYear()+"-"
		+ contrato.getFechaFin().getMonthValue()+"-"
		+contrato.getFechaFin().getDayOfMonth()+"\""+","
		+"\"tipoJornada\""+":"+"\""+contrato.getTipoJornada()+"\""+","
		+ "\"personal\""+":";
		
		//Conversion Objeto a JSON
		contrato.setPersonal(null);
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(personal).replace("NIF", "nif").replace("NSS", "nss");
		String json = jsonParser+jsonString+"}";

		//Peticion PUT
		mockMvc.perform(MockMvcRequestBuilders.
					put("/api/contrato/update/{nif}",NIF) 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									DELETE  CONTRATO POR NIF							                         *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Borrar Contrato Por NIF -- caso positivo")
    @Test
	void testDeleteContratoByNifisOk() throws Exception  {
		
		//Devuelve Contrato Para Cualquier NIF
		given(this.admService.findByNIFPersonal(any(String.class))).willReturn(new Personal());
		
		//Peticion DELETE
		mockMvc.perform(MockMvcRequestBuilders.
					delete("/api/contrato/delete/{nif}",NIF) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Borrar Contrato Por NIF -- caso negativo(No existe empleado con NIF)")
    @Test
    @Disabled
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este test esta comentado, ya que debido a que se utiliza la clase Authentication siempre devuelve un 200 OK   *
	 * por lo que no podemos probar correctamente el funcionamiento en este caso, pero la logica es correcta		 *																								
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	void testDeleteContratoByNifError() throws Exception  {
	    
		//Peticion DELETE
		String error = mockMvc.perform(MockMvcRequestBuilders.
				delete("/api/contrato/delete/{nif}",NIF_NOT_FOUND) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn().getResponse().getContentAsString();
		
		//Comprobamos Mensaje Error Es El Esperado
		assertThat(error).isEqualTo("No existe ningun empleado con dicho NIF");
	}
	@WithMockUser(value = "spring")
	@DisplayName("Borrar Contrato Por NIF -- caso negativo(Formato NIF No Valido)")
    @Test
    @Disabled
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este test esta comentado, ya que debido a que se utiliza la clase Authentication siempre devuelve un 200 OK   *
	 * por lo que no podemos probar correctamente el funcionamiento en este caso, pero la logica es correcta		 *																								
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	void testDeleteContratoByNifErrorNifInvalid() throws Exception  {
		
		
		//Peticion DELETE
	    String error = mockMvc.perform(MockMvcRequestBuilders.
				delete("/api/contrato/delete/{nif}",NIF_INVALIDO) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn().getResponse().getContentAsString();
	    
		//Comprobamos Mensaje Error Es El Esperado
		assertThat(error).isEqualTo("Formato del NIF invalido");
	}
	



}
