package org.springframework.gresur.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.ExceptionHandlerConfiguration;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = ProductoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class,ExceptionHandlerConfiguration.class})
class ProductoControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserRepository userRepository;
	
	@MockBean
	ProductoService productoService;
	
	@MockBean
	NotificacionService notificacionService;
	
	@MockBean
	AdministradorService adminService;
	
	private Pageable pageable;
	
	@BeforeEach
	void setUp() throws Exception {
		
		pageable = new Pageable();
		pageable.setDefaultPageSize(0);
		pageable.setMaxPageSize(10);
		
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET PRODUCTOS								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Todos Los Productos")
    @Test
	void testGetProductosIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/producto") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET PRODUCTOS	POR NOMBRE								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Productos Por Nombre")
    @Test
	void testGetProductosByNameIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/producto/{nombre}","blanco") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT EDITAR PRODUCTO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Datos De Un Producto -- caso positivo")
    @Test
	void testPostEditarProductoIsOk() throws Exception  {
		given(this.productoService.findById(any(Long.class))).willReturn(new Producto());
		String json = "{\"id\":77,\"nombre\":\"Estufa de kw rojo\",\"descripcion\":\"ESTUFA.\","
				+ "\"unidad\":\"UNIDADES\",\"stock\":3,\"stockSeguridad\":1,\"precioVenta\":995,"
				+ "\"precioCompra\":900,\"alto\":0.855,\"ancho\":0.885,\"profundo\":0.3,\"pesoUnitario\":17,"
				+ "\"estanteria\":{\"id\":3,\"categoria\":\"CALEFACCION\",\"capacidad\":600},"
				+ "\"urlimagen\":\"foto8.png\"}";
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/producto/save") 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Datos De Un Producto -- caso negativo(Producto No Valido)")
    @Test
	void testPostEditarProductoErrorInvalid() throws Exception  {
		given(this.productoService.findById(any(Long.class))).willReturn(new Producto());
		String json = "{\"id\":77,\"nombre\":\"\",\"descripcion\":\"ESTUFA.\",\"unidad\":\"UNIDADES\","
				+ "\"stock\":3,\"stockSeguridad\":1,\"precioVenta\":995,\"precioCompra\":900,"
				+ "\"alto\":0.855,\"ancho\":0.885,\"profundo\":0.3,\"pesoUnitario\":17,"
				+ "\"estanteria\":{\"id\":3,\"categoria\":\"CALEFACCION\",\"capacidad\":600},\"urlimagen\":\"foto8.png\"}";
		String error = mockMvc.perform(MockMvcRequestBuilders.
					post("/api/producto/save") 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertThat(error).isEqualTo("No puede ser vacio");

	}
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Datos De Un Producto -- caso negativo(Producto No Encontrado)")
    @Test
	void testPosEditarProductoErrorNotFound() throws Exception  {
		String json = "{\"id\":77,\"nombre\":\"\",\"descripcion\":\"ESTUFA.\",\"unidad\":\"UNIDADES\","
				+ "\"stock\":3,\"stockSeguridad\":1,\"precioVenta\":995,\"precioCompra\":900,\"alto\":0.855,"
				+ "\"ancho\":0.885,\"profundo\":0.3,\"pesoUnitario\":17,"
				+ "\"estanteria\":{\"id\":3,\"categoria\":\"CALEFACCION\",\"capacidad\":600},\"urlimagen\":\"foto8.png\"}";
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/producto/save") 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST NUEVO PRODUCTO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Agregar Nuevo Producto -- caso positivo")
    @Test
	void testPostNuevoProductoIsOk() throws Exception  {
		given(this.productoService.findById(any(Long.class))).willReturn(new Producto());
		String json = "{\"nombre\":\"Estufa de kw rojo\",\"descripcion\":\"ESTUFA.\","
				+ "\"unidad\":\"UNIDADES\",\"stock\":3,\"stockSeguridad\":1,\"precioVenta\":995,"
				+ "\"precioCompra\":900,\"alto\":0.855,\"ancho\":0.885,\"profundo\":0.3,\"pesoUnitario\":17,"
				+ "\"estanteria\":{\"id\":3,\"categoria\":\"CALEFACCION\",\"capacidad\":600},"
				+ "\"urlimagen\":\"foto8.png\"}";
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/producto/add") 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Agregar Nuevo Producto -- caso negativo(Producto Invalido)")
    @Test
	void testPostNuevoProductoError() throws Exception  {
		given(this.productoService.findById(any(Long.class))).willReturn(new Producto());
		String json = "{\"nombre\":\"\",\"descripcion\":\"ESTUFA.\","
				+ "\"unidad\":\"UNIDADES\",\"stock\":3,\"stockSeguridad\":1,\"precioVenta\":995,"
				+ "\"precioCompra\":900,\"alto\":0.855,\"ancho\":0.885,\"profundo\":0.3,\"pesoUnitario\":17,"
				+ "\"estanteria\":{\"id\":3,\"categoria\":\"CALEFACCION\",\"capacidad\":600},"
				+ "\"urlimagen\":\"foto8.png\"}";
		String error = mockMvc.perform(MockMvcRequestBuilders.
					post("/api/producto/add") 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertThat(error).isEqualTo("No puede ser vacio");

	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET ALL PRODUCTOS PAGINABLES								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Productos Paginados")
    @Test
	void testGetProductosPaginableIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/producto/paged") 
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET ALL PRODUCTOS PAGINABLES POR CATEGORIA							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Productos Paginados Por Categoria")
    @Test
	void testGetProductosPaginableByCategoriaIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/producto/paged/{category}","BANOS") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET ALL PRODUCTOS PAGINABLES POR NOMBRE							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Productos Paginados Por Nombre")
    @Test
	void testGetProductosPaginableByNameIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/producto/pagedName/{string}","CEMASDSDADENTO") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET ALL PRODUCTOS PAGINABLES ORDENADOS							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Productos Paginados Ordenados")
    @Test
	void testGetProductosPaginableOrderedIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/producto/pagedOrd") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET ALL PRODUCTOS PAGINABLES ORDENADOS POR CATEGORIA							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Productos Paginados Ordenados Por Categoria")
    @Test
	void testGetProductosPaginableOrderedByCategoriaIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/producto/pagedOrd/{category}","BANOS") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET ALL PRODUCTOS PAGINABLES ORDENADOS POR NOMBRE							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Productos Paginados Ordenados Por Categoria")
    @Test
	void testGetProductosPaginableOrderedByNameIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
					get("/api/producto/pagedNameOrd/{string}","CEMENTO") 
					.with(csrf())
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST NOTIFICACION CUANDO HAY BAJO STOCK						 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Enviar Notifacion Cuando Exista Bajo Stock -- caso positivo")
    @Test
	void testPostNotificacionBajoStockIsOk() throws Exception  {
		String json = "{\"nombre\":\"Estufa de kw rojo\",\"descripcion\":\"ESTUFA.\","
				+ "\"unidad\":\"UNIDADES\",\"stock\":3,\"stockSeguridad\":1,\"precioVenta\":995,"
				+ "\"precioCompra\":900,\"alto\":0.855,\"ancho\":0.885,\"profundo\":0.3,\"pesoUnitario\":17,"
				+ "\"estanteria\":{\"id\":3,\"categoria\":\"CALEFACCION\",\"capacidad\":600},"
				+ "\"urlimagen\":\"foto8.png\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/producto/notiStock/{almacenAdm}",1L) 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Enviar Notifacion Cuando Exista Bajo Stock -- caso negativo")
    @Test
	void testPostNotificacionBajoStockError() throws Exception  {
		String json = "{\"nombre\":\"\",\"descripcion\":\"ESTUFA.\","
				+ "\"unidad\":\"UNIDADES\",\"stock\":3,\"stockSeguridad\":1,\"precioVenta\":995,"
				+ "\"precioCompra\":900,\"alto\":0.855,\"ancho\":0.885,\"profundo\":0.3,\"pesoUnitario\":17,"
				+ "\"estanteria\":{\"id\":3,\"categoria\":\"CALEFACCION\",\"capacidad\":600},"
				+ "\"urlimagen\":\"foto8.png\"}";

		mockMvc.perform(MockMvcRequestBuilders.
					post("/api/producto/notiStock/{almacenAdm}",1L) 
					.with(csrf())
					.characterEncoding("utf-8")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	
	
}
