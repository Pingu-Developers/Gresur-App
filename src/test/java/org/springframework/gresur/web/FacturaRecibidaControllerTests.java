package org.springframework.gresur.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.gresur.model.Concepto;
import org.springframework.gresur.model.FacturaRecibida;
import org.springframework.gresur.model.ITV;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.Proveedor;
import org.springframework.gresur.model.Reparacion;
import org.springframework.gresur.model.ResultadoITV;
import org.springframework.gresur.model.Seguro;
import org.springframework.gresur.model.TipoSeguro;
import org.springframework.gresur.model.Unidad;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
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

@WebMvcTest(controllers = FacturaRecibidaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class, ExceptionHandlerConfiguration.class})
class FacturaRecibidaControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RolRepository roleRepository;
	
	@MockBean
	PasswordEncoder encoder;
	
	@MockBean
	FacturaRecibidaService facturaRecibidaService;
	
	@MockBean
	ProductoService productoService;
	
	@MockBean
	LineasFacturaService lineaFacturaService;
	
	@MockBean
	SeguroService seguroService;
	
	@MockBean
	ITVService itvService;
	
	@MockBean
	ReparacionService reparacionService;
	
	@Mock
	Tuple2<FacturaRecibida,List<Tuple2<Long,Integer>>> data1;
	@Mock
	Tuple2<FacturaRecibida,List<Tuple2<Long,Integer>>> mocktupla;
	@Mock
	FacturaRecibida facturaRecibida;
	@Mock
	Proveedor proveedor;
	@Mock
	Producto azulejo;
	@Mock
	List<Tuple2<Long,Integer>> lineas;
	@Mock
	LineaFactura lineaFactura;

	
	@Mock
	Seguro seguro;
	@Mock
	Tuple2<FacturaRecibida,Seguro> data2;
	@Mock
	Vehiculo vehiculo;
	
	@Mock
	Tuple2<FacturaRecibida, ITV> data3;
	@Mock
	ITV itv;
	
	@Mock
	Tuple2<FacturaRecibida, Reparacion> data4;
	@Mock
	Reparacion reparacion;
	
	@BeforeEach
	void setUp() throws Exception {

		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST FRA REPOSICION															 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura reposicion -- caso positivo")
	@Test
	void createReposicionIsOk() throws Exception{
		azulejo = new Producto();
		azulejo.setAlto(0.2);
		azulejo.setAncho(0.2);
		azulejo.setProfundo(0.1);
		azulejo.setDescripcion("Azulejo de Decoracion Pared de Cocina");
		azulejo.setNombre("Azulejo ugsi");
		azulejo.setPesoUnitario(0.1);
		azulejo.setPrecioCompra(12.);
		azulejo.setPrecioVenta(15.);
		azulejo.setStock(5);
		azulejo.setStockSeguridad(3);
		azulejo.setUnidad(Unidad.UNIDADES);
		azulejo.setURLImagen("docs/imgs/azulejococina.png");
		azulejo.setId(12L);
		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.REPOSICION_STOCK);
		facturaRecibida.setDescripcion("Reposicion de stock");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);
		
		data1=new Tuple2<FacturaRecibida, List<Tuple2<Long,Integer>>>();
		lineas = new ArrayList<>();
		
		Tuple2<Long, Integer> l1 = new Tuple2<Long, Integer>();
		l1.setE1(12L);
		l1.setE2(1);
		lineas.add(l1);
				
		data1.setE1(facturaRecibida);
		data1.setE2(lineas);
		
		lineaFactura = new LineaFactura();
		lineaFactura.setCantidad(1);
		lineaFactura.setId(1L);
		lineaFactura.setPrecio(15.0);
		lineaFactura.setProducto(azulejo);
		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willReturn(facturaRecibida);
		given(this.productoService.findById(any(Long.class))).willReturn(azulejo);
		given(this.productoService.save(any(Producto.class))).willReturn(azulejo);
		given(this.lineaFacturaService.save(any(LineaFactura.class))).willReturn(lineaFactura);

		Gson gson = new Gson();
		String jsonString = gson.toJson(data1);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/repo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura reposicion -- caso negativo")
	@Test
	void createReposicionBadRequest() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.REPOSICION_STOCK);
		facturaRecibida.setDescripcion("Reposicion de stock");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);
		
		data1=new Tuple2<FacturaRecibida, List<Tuple2<Long,Integer>>>();
		lineas = new ArrayList<>();
		
		Tuple2<Long, Integer> l1 = new Tuple2<Long, Integer>();
		l1.setE1(12L);
		l1.setE2(1);
		lineas.add(l1);
				
		data1.setE1(facturaRecibida);
		data1.setE2(lineas);
		
		lineaFactura = new LineaFactura();
		lineaFactura.setCantidad(1);
		lineaFactura.setId(1L);
		lineaFactura.setPrecio(15.0);
		lineaFactura.setProducto(azulejo);
		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willReturn(facturaRecibida);
		given(this.productoService.findById(any(Long.class))).willThrow(new NullPointerException());
		given(this.productoService.save(any(Producto.class))).willThrow(new NullPointerException());
		given(this.lineaFacturaService.save(any(LineaFactura.class))).willReturn(lineaFactura);
		
		Gson gson = new Gson();
		String jsonString = gson.toJson(data1);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/repo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST FRA SEGURO																 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura seguro -- caso positivo")
	@Test
	void createRecibidaSeguroIsOk() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibida.setDescripcion("Pago seguro valido");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);
		
		seguro = new Seguro();
		seguro.setCompania("Pelayo");
		//seguro.setFechaContrato(LocalDate.of(2022, 1, 1));
		//seguro.setFechaExpiracion(LocalDate.of(2023, 1, 1));
		seguro.setId(1L);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		
		vehiculo = new Vehiculo();
		seguro.setVehiculo(vehiculo);
		
		seguro.setRecibidas(facturaRecibida);
		
		data2 = new Tuple2<FacturaRecibida, Seguro>();
		data2.setE1(facturaRecibida);
		data2.setE2(seguro);
		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willReturn(facturaRecibida);
		given(this.seguroService.save(any(Seguro.class))).willReturn(seguro);

		Gson gson = new Gson();
		String jsonString = gson.toJson(data2);
		System.out.println(jsonString);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/seguro")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura seguro -- caso negativo")
	@Test
	void createRecibidaSeguroError() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibida.setDescripcion("Pago seguro no valido");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);
		
		seguro = new Seguro();
		seguro.setCompania("Pelayo");
		//seguro.setFechaContrato(LocalDate.of(2022, 1, 1));
		//seguro.setFechaExpiracion(LocalDate.of(2023, 1, 1));
		seguro.setId(1L);
		seguro.setTipoSeguro(TipoSeguro.TODORRIESGO);
		
		vehiculo = new Vehiculo();
		seguro.setVehiculo(vehiculo);
		
		seguro.setRecibidas(facturaRecibida);
		
		data2 = new Tuple2<FacturaRecibida, Seguro>();
		data2.setE1(facturaRecibida);
		data2.setE2(seguro);
		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willThrow(new NullPointerException());
		given(this.seguroService.save(any(Seguro.class))).willThrow(new NullPointerException());

		Gson gson = new Gson();
		String jsonString = gson.toJson(data2);
		System.out.println(jsonString);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/seguro")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST FRA ITV																 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura itv -- caso positivo")
	@Test
	void createRecibidaItvIsOk() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibida.setDescripcion("Pago seguro valido");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);
		
		itv = new ITV();
		itv.setResultado(ResultadoITV.FAVORABLE);
		itv.setRecibidas(facturaRecibida);
		itv.setId(1L);
		
		vehiculo = new Vehiculo();
		itv.setVehiculo(vehiculo);
		
		itv.setRecibidas(facturaRecibida);
		
		data3 = new Tuple2<FacturaRecibida, ITV>();
		data3.setE1(facturaRecibida);
		data3.setE2(itv);
		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willReturn(facturaRecibida);
		given(this.itvService.save(any(ITV.class))).willReturn(itv);

		Gson gson = new Gson();
		String jsonString = gson.toJson(data3);
		System.out.println(jsonString);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/seguro")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura itv -- caso negativo")
	@Test
	void createRecibidaItvError() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibida.setDescripcion("Pago seguro valido");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);
		
		itv = new ITV();
		itv.setResultado(ResultadoITV.FAVORABLE);
		itv.setRecibidas(facturaRecibida);
		itv.setId(1L);
		
		vehiculo = new Vehiculo();
		itv.setVehiculo(vehiculo);
		
		itv.setRecibidas(facturaRecibida);
		
		data3 = new Tuple2<FacturaRecibida, ITV>();
		data3.setE1(facturaRecibida);
		data3.setE2(itv);
		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willThrow(new NullPointerException());
		given(this.itvService.save(any(ITV.class))).willThrow(new NullPointerException());

		Gson gson = new Gson();
		String jsonString = gson.toJson(data3);
		System.out.println(jsonString);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/seguro")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST FRA REPARACION															 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura reparacion -- caso positivo")
	@Test
	void createRecibidaReparacionIsOk() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibida.setDescripcion("Pago seguro valido");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);
		
		reparacion = new Reparacion();
		reparacion.setDescripcion("Cambio de aceite");
		reparacion.setId(1L);
		

		
		vehiculo = new Vehiculo();
		reparacion.setVehiculo(vehiculo);
		
		reparacion.setRecibidas(facturaRecibida);
		
		data4 = new Tuple2<FacturaRecibida, Reparacion>();
		data4.setE1(facturaRecibida);
		data4.setE2(reparacion);
		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willReturn(facturaRecibida);
		given(this.reparacionService.save(any(Reparacion.class))).willReturn(reparacion);

		Gson gson = new Gson();
		String jsonString = gson.toJson(data4);
		System.out.println(jsonString);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/reparacion")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura reparacion -- caso negativo")
	@Test
	void createRecibidaReparacionError() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.GASTOS_VEHICULOS);
		facturaRecibida.setDescripcion("Pago seguro valido");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);
		
		reparacion = new Reparacion();
		reparacion.setDescripcion("Cambio de aceite");
		reparacion.setId(1L);
		

		
		vehiculo = new Vehiculo();
		reparacion.setVehiculo(vehiculo);
		
		reparacion.setRecibidas(facturaRecibida);
		
		data4 = new Tuple2<FacturaRecibida, Reparacion>();
		data4.setE1(facturaRecibida);
		data4.setE2(reparacion);
		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willThrow(new NullPointerException());
		given(this.reparacionService.save(any(Reparacion.class))).willThrow(new NullPointerException());

		Gson gson = new Gson();
		String jsonString = gson.toJson(data4);
		System.out.println(jsonString);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/reparacion")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST FRA OTRO																 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura otro -- caso positivo")
	@Test
	void createRecibidaOtroIsOk() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.OTROS);
		facturaRecibida.setDescripcion("Descripcion");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);

		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willReturn(facturaRecibida);

		Gson gson = new Gson();
		String jsonString = gson.toJson(facturaRecibida);
		System.out.println(jsonString);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/otro")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	
	@WithMockUser(value = "spring")
	@DisplayName("POST Crear factura otro -- caso negativo")
	@Test
	void createRecibidaOtroError() throws Exception{		
		proveedor = new Proveedor();
		proveedor.setDireccion("Poligono Indrustrial El Gastor - Cadiz");
		proveedor.setEmail("email@email.com");
		proveedor.setIBAN("ES1120952845103784661664");
		proveedor.setId(1L);
		proveedor.setName("Borja Hernando Canovas");
		proveedor.setNIF("26690085B");
		proveedor.setTlf("691533234");
		
		facturaRecibida = new FacturaRecibida();
		facturaRecibida.setConcepto(Concepto.OTROS);
		facturaRecibida.setDescripcion("Descripcion");
		facturaRecibida.setEstaPagada(true);
		facturaRecibida.setImporte(0.0);
		facturaRecibida.setProveedor(proveedor);

		
		given(this.facturaRecibidaService.save(any(FacturaRecibida.class))).willThrow(new NullPointerException());

		Gson gson = new Gson();
		String jsonString = gson.toJson(facturaRecibida);
		System.out.println(jsonString);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/facturaRecibida/otro")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf()))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	

}
