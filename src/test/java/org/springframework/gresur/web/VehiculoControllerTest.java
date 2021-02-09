package org.springframework.gresur.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.TransportistaRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.PersonalService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

@WebMvcTest(controllers = VehiculoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class,ExceptionHandlerConfiguration.class})
class VehiculoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	 ITVService itvService;
	
	@MockBean
	SeguroService seguroService;
	
	@MockBean
	ReparacionService reparacionService;
	
	@MockBean
	PersonalService<Transportista, TransportistaRepository> personalService;	
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	VehiculoService vehiculoService;
	
	private Vehiculo vehiculo;
	
	@BeforeEach
	void setUp() throws Exception {
		
		vehiculo = new Vehiculo();
				
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 VEHICULOS POR ITV/SEGURO DISPONIBILIDAD								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Consultar Vehiculos Disponibles Segun ITV y Seguro")
    @Test
	void testGetVehiculoDisponibilidadisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/vehiculo") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 TODOS VEHICULOS 						                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Consultar Todos Vehiculos")
    @Test
	void testGetAllVehiculoisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/vehiculo/all") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 TODOS VEHICULOS SIMPLE					                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Consultar Todos Vehiculos Simple")
    @Test
	void testGetAllVehiculoSimpleisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/vehiculo/allsimple") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST  VEHICULO DISPONIBILIDAD					                             *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Ver disponibilidad de un vehiculo -- caso positivo")
    @Test
	void testPostVehiculoDisponibilidadisOk() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(231.);
		vehiculo.setImagen("/img/camion.png");
		vehiculo.setMatricula("1909GND");
		vehiculo.setMMA(700.);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		
		//Creacion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/vehiculo/info") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(value = "spring")
	@DisplayName("Ver disponibilidad de un vehiculo -- caso negativo")
    @Test
	void testPostVehiculoDisponibilidadError() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(231.);
		vehiculo.setImagen("/img/camion.png");
		vehiculo.setMatricula("1909GND");
		vehiculo.setMMA(-1.);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		
		//Creacion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/vehiculo/info") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST  VEHICULO 					                                             *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Añadir nuevo  vehiculo -- caso positivo")
    @Test
	void testPostVehiculoisOk() throws Exception  {
	
		//Creacion Vehiculo
		vehiculo.setCapacidad(231.);
		vehiculo.setImagen("/img/camion.png");
		vehiculo.setMatricula("1909GND");
		vehiculo.setMMA(700.);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		
		//Creacion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/vehiculo/add") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(value = "spring")
	@DisplayName("Añadir nuevo  vehiculo -- caso negativo")
    @Test
	void testPostVehiculoError() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(231.);
		vehiculo.setImagen("/img/camion.png");
		vehiculo.setMatricula("1909GND");
		vehiculo.setMMA(700.);
		vehiculo.setTipoVehiculo(null);
		
		//Creacion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/vehiculo/add") 
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString)
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET ALL TIPOS  VEHICULO 					                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Consultar Todos Vehiculos Por Tipo")
    @Test
	void testGetAllTiposVehiculoisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/vehiculo/allTiposVehiculos") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									DELETE  VEHICULO 					                                         *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Borrar un vehiculo -- caso positivo")
    @Test
	void testDeleteVehiculoisOk() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(231.);
		vehiculo.setImagen("/img/camion.png");
		vehiculo.setMatricula("4090GND");
		vehiculo.setMMA(700.);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		
		//Peticion DELETE
		mockMvc.perform(MockMvcRequestBuilders.
					delete("/api/vehiculo/delete/{matricula}",vehiculo.getMatricula()) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Borrar un vehiculo -- caso negativo")
    @Test
	void testDeleteVehiculoError() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(231.);
		vehiculo.setImagen("/img/camion.png");
		vehiculo.setMatricula("EE4090GND");
		vehiculo.setMMA(700.);
		vehiculo.setTipoVehiculo(TipoVehiculo.CAMION);
		
		//Peticion DELETE
		String error = mockMvc.perform(MockMvcRequestBuilders.
					delete("/api/vehiculo/delete/{matricula}",vehiculo.getMatricula()) 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn().getResponse().getContentAsString();
		
		//Comprobamos Mensaje Error Es El Esperado
		assertThat(error).isEqualTo("Formato de matricula no valido");

	}







}
