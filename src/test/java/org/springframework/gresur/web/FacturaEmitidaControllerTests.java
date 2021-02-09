package org.springframework.gresur.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.gresur.configuration.ExceptionHandlerConfiguration;
import org.springframework.gresur.configuration.SecurityConfiguration;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.Unidad;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
@WebMvcTest(controllers = FacturaEmitidaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class, ExceptionHandlerConfiguration.class})
class FacturaEmitidaControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RolRepository roleRepository;
	
	@MockBean
	PasswordEncoder encoder;
	
	@MockBean
	FacturaEmitidaService facturaEmitidaService;
	
	@MockBean
	ProductoService productoService;
	
	@MockBean
	LineasFacturaService lineaFacturaService;
	
	@MockBean
	Cliente cliente;
	
	@Mock
	List<FacturaEmitida> listaFacturas;
	@Mock
	FacturaEmitida facturaEmitida;
	@Mock
	Tuple2<Long, LocalDate> data1;
	@Mock
	FacturaEmitida facturaEmitida2;
	@Mock
	Dependiente dependiente;
	@Mock
	Producto producto;
	@Mock
	LineaFactura lineaFactura;
	@Mock
	LineaFactura lineaFactura2;
	@Mock
	List<LineaFactura> lineas;
	@Mock
	List<LineaFactura> lineas2;
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET FACTURA BY CLIENTE ID													*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("GET Factura Emitida -- caso positivo")
	@Test
	void getFacturasClienteIsOk() throws Exception{
		listaFacturas = new ArrayList<>();
		
		given(this.facturaEmitidaService.findFacturasByCliente(any(Long.class))).willReturn(listaFacturas);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/facturaEmitida/{id}", 1L)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("GET Factura Emitida -- caso negativo")
	@Test
	void getFacturasClienteBadRequest() throws Exception{
		listaFacturas = null;
		
		given(this.facturaEmitidaService.findFacturasByCliente(any(Long.class))).willReturn(listaFacturas);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/facturaEmitida/{id}", 1L)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	 
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST (que realmente es un GET) FACTURAS CLIENTE POR FECHA					*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@Disabled
	@WithMockUser(value = "spring")
	@DisplayName("GET (*) Facturas cliente por fecha -- caso positivo")
	@Test
	void getFacturasClienteAndFechaIsOk() throws Exception{
		listaFacturas = new ArrayList<>();

		data1 = new Tuple2<Long, LocalDate>();
		data1.setE1(1L);
		data1.setE2(LocalDate.of(2020, 12, 12));
		
		given(this.facturaEmitidaService.findFacturasByClienteAndFecha(any(Long.class), any(LocalDate.class))).willReturn(listaFacturas);
		Gson gson = new Gson();
		String jsonString = gson.toJson(data1);
		System.out.println("{\"e1\":\"1L\",\"e2\":\"2020-12-12\"}");
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaEmitida/clienteFecha")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	
	/*@WithMockUser(value = "spring")
	@DisplayName("GET (*) Facturas cliente por fecha -- caso negativo")
	@Test
	void getFacturasClienteAndFechaBadRequest() throws Exception{
		listaFacturas = new ArrayList<>();

		data1 = new Tuple2<Long, LocalDate>();
		data1.setE1(1L);
		data1.setE2(LocalDate.of(2020, 12, 12));
		
		given(this.facturaEmitidaService.findFacturasByClienteAndFecha(any(Long.class), any(LocalDate.class))).willReturn(null);
		Gson gson = new Gson();
		String jsonString = gson.toJson(data1);
		System.out.println("{\"e1\":\"1L\",\"e2\":\"2020-12-12\"}");
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaEmitida/clienteFecha")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	*/
	
	//AQUI VA LA DEVOLUCION
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET FACTURA POR NUM FACTURA													*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("GET Factura por numFactura -- caso positivo")
	@Test
	void getFacturaByNumFacturaIsOk() throws Exception{
		facturaEmitida = new FacturaEmitida();
		facturaEmitida.setNumFactura("E-1");
		given(this.facturaEmitidaService.findByNumFactura(any(String.class))).willReturn(facturaEmitida);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/facturaEmitida/cargar/{numFactura}", "E-1")
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("GET Factura por numFactura -- caso negativo")
	@Test
	void getFacturaByNumFacturaInvalid() throws Exception{
		facturaEmitida = new FacturaEmitida();
		facturaEmitida.setNumFactura("E-1");
		given(this.facturaEmitidaService.findByNumFactura(any(String.class))).willReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/facturaEmitida/cargar/{numFactura}", "E-1")
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST RECTIFICAR FACTURA														*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@Disabled
	@WithMockUser(value = "spring")
	@DisplayName("POST Rectificar factura -- caso positivo")
	@Test
	void rectificarFacturaIsOk() throws Exception{
		listaFacturas = new ArrayList<>();
		facturaEmitida = new FacturaEmitida();
		facturaEmitida2 = new FacturaEmitida();
		lineaFactura = new LineaFactura();
		lineaFactura2 = new LineaFactura();
		lineas = new ArrayList<>();
		lineas2 = new ArrayList<>();
		producto = new Producto();
		cliente = new Cliente();
		dependiente = new Dependiente();

		producto.setAlto(0.2);
		producto.setAncho(0.2);
		producto.setProfundo(0.1);
		producto.setDescripcion("Azulejo de Decoracion Pared de Cocina");
		producto.setNombre("Azulejo ugsi");
		producto.setPesoUnitario(0.1);
		producto.setPrecioCompra(12.);
		producto.setPrecioVenta(15.);
		producto.setStock(5);
		producto.setStockSeguridad(3);
		producto.setUnidad(Unidad.UNIDADES);
		producto.setURLImagen("docs/imgs/azulejococina.png");
		producto.setId(1L);
		
		//FACTURA ORIGINAL
		facturaEmitida.setCliente(cliente);
		facturaEmitida.setDependiente(dependiente);
		facturaEmitida.setDescripcion("descripcion");
		facturaEmitida.setEstaPagada(true);
		facturaEmitida.setId(1L);
		facturaEmitida.setImporte(0.0);
		facturaEmitida.setNumFactura("E-1");
		
		lineaFactura.setCantidad(1);
		lineaFactura.setId(1L);
		lineaFactura.setPrecio(12.);
		lineaFactura.setProducto(producto);
		
		lineas = new ArrayList<LineaFactura>();
		lineas.add(lineaFactura);
		facturaEmitida.setLineasFacturas(lineas);
		
		//FACTURA RECTIFICADA
		facturaEmitida2.setCliente(cliente);
		facturaEmitida2.setDependiente(dependiente);
		facturaEmitida2.setDescripcion("descripcion");
		facturaEmitida2.setEstaPagada(true);
		facturaEmitida2.setId(2L);
		facturaEmitida2.setImporte(0.0);
		facturaEmitida2.setNumFactura("RCTE-1");
		
		lineaFactura2.setCantidad(2);
		lineaFactura2.setId(1L);
		lineaFactura2.setPrecio(12.);
		lineaFactura2.setProducto(producto);
		
		lineas2 = new ArrayList<LineaFactura>();
		lineas2.add(lineaFactura2);
		facturaEmitida2.setLineasFacturas(lineas2);
		
		//Marcamos como rectificativa
		//facturaEmitida.setRectificativa(facturaEmitida2);
		
		
		listaFacturas.add(facturaEmitida2);
		
		given(this.facturaEmitidaService.findByNumFactura(any(String.class))).willReturn(facturaEmitida);
		given(this.facturaEmitidaService.save(any(FacturaEmitida.class))).willReturn(facturaEmitida2);
		given(this.productoService.findById(any(Long.class))).willReturn(producto);
		//given(this.lineaFacturaService.saveAll(Mockito.anyList())).willreturn(null);
		//given(this.lineaFacturaService.save(any(LineaFactura.class))).willReturn(null);
		Gson gson = new Gson();
		String jsonString = gson.toJson(facturaEmitida2);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaEmitida/rectificar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	
	
	

}
