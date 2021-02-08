package org.springframework.gresur.web;


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
import org.springframework.gresur.model.Almacen;
import org.springframework.gresur.model.Categoria;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.Dependiente;
import org.springframework.gresur.model.EstadoPedido;
import org.springframework.gresur.model.Estanteria;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.model.LineaFactura;
import org.springframework.gresur.model.Pedido;
import org.springframework.gresur.model.Producto;
import org.springframework.gresur.model.TipoVehiculo;
import org.springframework.gresur.model.Transportista;
import org.springframework.gresur.model.Unidad;
import org.springframework.gresur.model.Vehiculo;
import org.springframework.gresur.repository.TransportistaRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.ClienteService;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.service.PedidoService;
import org.springframework.gresur.service.PersonalService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.gresur.util.Tuple2;
import org.springframework.gresur.util.Tuple3;
import org.springframework.gresur.util.Tuple4;
import org.springframework.gresur.util.Tuple5;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = PedidoController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= {SecurityConfiguration.class,ExceptionHandlerConfiguration.class})
class PedidoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	PedidoService pedidoService;
	
	@MockBean
	VehiculoService vehiculoService;
	
	@MockBean
	ClienteService clienteService;
	
	@MockBean
	ProductoService productoService;
	
	@MockBean
	FacturaEmitidaService facturaEmitidaService;
	
	@MockBean
	LineasFacturaService lineaFacturaService;

	@MockBean
	NotificacionService notificacionService;

	@MockBean
	AdministradorService adminService;

	@MockBean
	UserRepository userRepository;

	@MockBean
	PersonalService<Transportista, TransportistaRepository> personalService;
	
	private Integer page = 0;
	private Integer size = 7;
	private String orden = "";
	private Long id = 2L;
	private String estado = "PREPARADO";
	private Tuple4<String, String, LocalDate, Tuple4<Double, Boolean, String, List<Tuple2<Long, Integer>>>> t4;
	private Tuple4<Double, Boolean, String, List<Tuple2<Long, Integer>>> t3;
	private List<Tuple2<Long, Integer>> listt2;
	private Tuple2<Long, Integer> t2;
	
	private List<Tuple3<Vehiculo,Double,Double>> ocupacionPedidosDelDia;
	private Tuple3<Vehiculo,Double,Double> tuplaOcupacionPedidosDelDia;
	private Vehiculo vehiculo;
	
	private List<Tuple5<Long, String, EstadoPedido, String, String>> pedidosDeHoy;
	private Tuple5<Long, String, EstadoPedido, String, String> tuplaPedidoDeHoy;
	
	private Pedido pedido;
	private Transportista transportista;
	@BeforeEach
	void setUp() throws Exception {
				
		t4 = new Tuple4<String, String, LocalDate, Tuple4<Double,Boolean,String,List<Tuple2<Long,Integer>>>>();
		t4.setE1("Calle Larios n25");
		t4.setE2("PREPARADO");
		t4.setE3(LocalDate.now().minusDays(1));
		
		t3 = new Tuple4<Double, Boolean, String, List<Tuple2<Long,Integer>>>();
		t3.setE1(2500.);
		t3.setE2(false);
		t3.setE3("20070284E");
		
		listt2 = new ArrayList<Tuple2<Long,Integer>>();
		
		t2 = new Tuple2<Long, Integer>();
		t2.setE2(25);
		t2.setE1(1L);
		
		listt2.add(t2);
		
		t3.setE4(listt2);
		t4.setE4(t3);
		
		ocupacionPedidosDelDia = new ArrayList<Tuple3<Vehiculo,Double,Double>>();
		tuplaOcupacionPedidosDelDia = new Tuple3<Vehiculo, Double, Double>();
		vehiculo = new Vehiculo();
		vehiculo.setCapacidad(2000.);
		vehiculo.setImagen("furgoneta.png");
		vehiculo.setMatricula("3090GND");
		vehiculo.setMMA(200.);
		vehiculo.setTipoVehiculo(TipoVehiculo.FURGONETA);
		tuplaOcupacionPedidosDelDia.setE1(vehiculo);
		tuplaOcupacionPedidosDelDia.setE2(200.);
		tuplaOcupacionPedidosDelDia.setE3(200.);
		ocupacionPedidosDelDia.add(tuplaOcupacionPedidosDelDia);
		
		pedidosDeHoy = new ArrayList<Tuple5<Long,String,EstadoPedido,String,String>>();
		tuplaPedidoDeHoy = new Tuple5<Long, String, EstadoPedido, String, String>();
		tuplaPedidoDeHoy.setE1(1L);
		tuplaPedidoDeHoy.setE2("Calle Alambique");
		tuplaPedidoDeHoy.setE3(EstadoPedido.PREPARADO);
		tuplaPedidoDeHoy.setE4("Paco Manuel Lopez");
		tuplaPedidoDeHoy.setE5("Camion");
		pedidosDeHoy.add(tuplaPedidoDeHoy);
		
		
		Almacen almacen = new Almacen();
		almacen.setCapacidad(1500.00);
		almacen.setDireccion("C/ Almacen");
		
		
		
		Estanteria estanteria = new Estanteria();
		estanteria.setAlmacen(almacen);
		estanteria.setCapacidad(550.00);
		estanteria.setCategoria(Categoria.AZULEJOS);
		
		Producto p1 = new Producto();
		p1.setAlto(1.1);
		p1.setAncho(1.9);
		p1.setDescripcion("Una descripcion de un producto");
		p1.setEstanteria(estanteria);
		p1.setNombre("Azulejo rojo");
		p1.setPesoUnitario(80.10);
		p1.setPrecioCompra(10.29);
		p1.setPrecioVenta(29.01);
		p1.setProfundo(1.2);
		p1.setStock(70);
		p1.setStockSeguridad(30);
		p1.setUnidad(Unidad.UNIDADES);
		
		Producto p2 = new Producto();
		p2.setAlto(1.1);
		p2.setAncho(1.9);
		p2.setDescripcion("Una descripcion de un producto");
		p2.setEstanteria(estanteria);
		p2.setNombre("Azulejo amarillo");
		p2.setPesoUnitario(80.10);
		p2.setPrecioCompra(10.29);
		p2.setPrecioVenta(29.01);
		p2.setProfundo(1.2);
		p2.setStock(35);
		p2.setStockSeguridad(30);
		p2.setUnidad(Unidad.UNIDADES);
		
		pedido = new Pedido();
		pedido.setDireccionEnvio("Calle España");
		pedido.setEstado(EstadoPedido.ENTREGADO);
		pedido.setFechaEnvio(LocalDate.of(2020, 10, 22));
		pedido.setFechaRealizacion(LocalDate.of(2020, 10, 10));
		pedido.setVehiculo(vehiculo);
	
		transportista = new Transportista();
		transportista.setName("Antonio Manuel Lopez");
		transportista.setDireccion("Calle Andalucia");
		transportista.setEmail("antonio@gmail.com");
		transportista.setNIF("30070284X");
		transportista.setNSS("123456789966");
		transportista.setTlf("278675482");
		pedido.setTransportista(transportista);
		
		FacturaEmitida fe = new FacturaEmitida();
		
		Cliente cliente = new Cliente();
		cliente.setDireccion("Calle");
		cliente.setEmail("cliente@mail.es");
		cliente.setName("Jose Luis");
		cliente.setNIF("54789663T");
		cliente.setTlf("657412397");
		
		fe.setCliente(cliente);
		
		Dependiente dependiente = new Dependiente();
		dependiente.setDireccion("C/ ejemplo");
		dependiente.setEmail("patata@gmail.com");
		dependiente.setImage("/resourdes/foto.png");
		dependiente.setName("Antonio Jose Ruiz Ruiz");
		dependiente.setNIF("12345678L");
		dependiente.setNSS("123456789012");
		dependiente.setTlf("123456789");
		
		fe.setDependiente(dependiente);
		
		fe.setDescripcion("Todo OK Jose Luis");
		
		fe.setEstaPagada(true);
		fe.setImporte(1500.);
		fe.setNumFactura("E5");
		List<LineaFactura> lf = new ArrayList<LineaFactura>();
		LineaFactura lf1 = new LineaFactura();
		LineaFactura lf2 = new LineaFactura();
		
		lf1.setCantidad(10);
		lf1.setFactura(fe);
		lf1.setPrecio(100.15);
		lf1.setProducto(p1);
		
		lf2.setCantidad(20);
		lf2.setFactura(fe);
		lf2.setPrecio(220.00);
		lf2.setProducto(p2);
		
		fe.setLineasFacturas(lf);
	
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	PAGE PEDIDO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Pagina Pedido")
    @Test
	void testGetPagePedidosisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/page={pageNo}&size={pageSize}&order={orden}",page,size,orden) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 PEDIDO	POR ID							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Pedido Por Id")
    @Test
	void testGetPedidosByIdisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/id/{id}",id) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 PEDIDO	POR ESTADO ORDENADO							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Pedido Por Estado Ordenado")
    @Test
	void testGetPedidosByEstadoisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/{estado}/page={pageNo}&size={pageSize}&order={orden}",estado,page,size,orden) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST	AÑADIR NUEVO  PEDIDO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Agregar Nuevo Pedido")
    @Test
    void testPostAddPedidosisOk() throws Exception  {
		String json = "{\"e1\":\"Calle Larios n25\",\"e2\":\"PREPARADO\","
				+ "\"e3\":\"2020-10-22\",\"e4\":{\"e1\":2500,\"e2\":false,"
				+ "\"e3\":\"20070284E\",\"e4\":[{\"e1\":1,\"e2\":25}]}}";

		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/pedido/add") 
				.with(csrf())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST	CANCELAR   PEDIDO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Cancelar Pedido Por Id -- caso positivo")
    @Test
	void testPostCancelarPedidoByIdisOk() throws Exception  {
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/pedido/{id}",8L) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Cancelar Pedido Por Id -- caso negativo")
    @Test
	void testPostCancelarPedidoByIdError() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/pedido/{id}",8L) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	OCUPACION   PEDIDOS DEL DIA								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Ver Ocupacion Pedidos Del Dia")
    @Test
	void testPostOcupacionDelDiaisOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/pedido/ocupacion")
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 PEDIDOS DEL DIA TRANSPORTISTA								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Ver Pedidos Del Dia Asociados Al Transportista")
    @Test
	void testGetPedidosTransportistaHoyIsOk() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(pedidosDeHoy);
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/hoy")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 FACTURA DE UN PEDIDO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Ver Pedidos Del Dia Asociados Al Transportista")
    @Test
	void testGetFacturaDePedidoIsOk() throws Exception  {
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/factura/{id}",8L)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT SET EN REPARTO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Poner En Reparto Un Pedido -- caso positivo")
    @Test
	void testPutSetEnRepartoPedidoIsOk() throws Exception  {
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/reparto/{id}",pedido.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Poner En Reparto Un Pedido - caso negativo")
    @Test
	void testPutSetEnRepartoPedidoError() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/reparto/{id}",8L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT SET EN ENTREGADO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Poner En Entregado Un Pedido -- caso positivo")
    @Test
	void testPutSetEnEntregadoPedidoIsOk() throws Exception  {
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/entregado/{id}",pedido.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Poner En Entregado Un Pedido -- caso negativo")
    @Test
	void testPutSetEnEntregadoPedidoError() throws Exception  {
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/entregado/{id}",8L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET PEDIDO POR ESTADO Y TRANSPORTISTA								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Pedido Por Estado y Transportista")
    @Test
	void testGetPedidoPorEstadoTransportistaIsOk() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/transportista/{estado}","PREPARADO")
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT SET ESTA PAGADO								 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Poner Pagado  Factura De Un Pedido -- caso positivo")
    @Test
	void testPutSetPagadoFacturaPedidoIsOk() throws Exception  {
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/pagado/{id}",pedido.getId())
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Poner Pagado  Factura De Un Pedido -- caso negativo")
    @Test
	void testsPutSetPagadoPedidoError() throws Exception  {
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/pagado/{id}",8L)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT UPDATE PEDIDO							 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Datos Pedido -- caso positivo")
    @Test
	void testPutUpdatePedidoisOk() throws Exception  {
		String json = "{\"id\":3,\"direccionEnvio\":\"Calle Paco Pepe Ruiz\",\"estado\":\"PREPARADO\",\"fechaRealizacion\":\"2020-12-24\","
				+ "\"fechaEnvio\":\"2021-01-01\",\"facturaEmitida\":{\"id\":3,\"numFactura\":\"E2\","
				+ "\"fechaEmision\":\"2020-12-22\",\"importe\":497.99,\"estaPagada\":true,\"lineasFacturas\":[{\"id\":7,"
				+ "\"cantidad\":1,\"precio\":98.99,\"producto\":{\"id\":6,\"nombre\":\"Lavabo con pedestal Charm\","
				+ "\"descripcion\":\"Lavabo\",\"unidad\":\"UNIDADES\",\"stock\":20,\"stockSeguridad\":5,\"precioVenta\":98.99,"
				+ "\"precioCompra\":90,\"alto\":0.71,\"ancho\":0.43,\"profundo\":0.48,\"pesoUnitario\":10,"
				+ "\"estanteria\":{\"id\":2,\"categoria\":\"BANOS\",\"capacidad\":550},\"urlimagen\":\"foto.png\"}},"
				+ "{\"id\":8,\"cantidad\":1,\"precio\":399,\"producto\":{\"id\":7,\"nombre\":\"Lavabo Zeus blanco\",\"descripcion\":\"LABA.\","
				+ "\"unidad\":\"UNIDADES\",\"stock\":5,\"stockSeguridad\":2,\"precioVenta\":399.99,\"precioCompra\":360,\"alto\":0.81,\"ancho\":0.12,\"profundo\":0.46,"
				+ "\"pesoUnitario\":11,\"estanteria\":{\"id\":2,\"categoria\":\"BANOS\",\"capacidad\":550},\"urlimagen\":\"foto.png\"}}],"
				+ "\"descripcion\":null,\"original\":null,\"dependiente\":{\"id\":4,\"name\":\"Jose Javier Mu\u00f1oz Jimenez\""
				+ ",\"email\":\"u0kvftny@talk21.com\",\"tlf\":\"688128113\",\"direccion\":\"Calle Via Nueva, 65, 21499, Palos De La Frontera(Huelva)\","
				+ "\"image\":\"foto.png\",\"nss\":\"210473213263\",\"nif\":\"64145337Q\"},\"cliente\":{\"id\":3,\"name\":\"Luis Olivares Dominguez\","
				+ "\"email\":\"rnu6e107@journalism.com\",\"tlf\":\"787983426\",\"direccion\":\"Calle Campo Horno 51, D\u00fadar, Granada, 18355\",\"nif\":\"44812413H\"}},"
				+ "\"vehiculo\":null,\"transportista\":{\"id\":6,\"name\":\"Agustin Pineda Rey\",\"email\":\"f8s9khjgx@btinternet.com\",\"tlf\":\"672910341\","
				+ "\"direccion\":\"Calle Carrera De Espa\u00f1a, 74, 28661, Valdemanco(madrid)\",\"image\":\"foto.png\",\"nss\":\"410473213263\",\"nif\":\"73929968X\"}}";
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/update")
				.with(csrf())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Datos Pedido -- caso negativo")
    @Test
	void testPutUpdatePedidoError() throws Exception  {
		pedido.setDireccionEnvio("");
		String json = "{\"id\":3,\"direccionEnvio\":\"\",\"estado\":\"PREPARADO\",\"fechaRealizacion\":\"2020-12-24\","
				+ "\"fechaEnvio\":\"2021-01-01\",\"facturaEmitida\":{\"id\":3,\"numFactura\":\"E2\","
				+ "\"fechaEmision\":\"2020-12-22\",\"importe\":497.99,\"estaPagada\":true,\"lineasFacturas\":[{\"id\":7,"
				+ "\"cantidad\":1,\"precio\":98.99,\"producto\":{\"id\":6,\"nombre\":\"Lavabo con pedestal Charm\","
				+ "\"descripcion\":\"Lavabo\",\"unidad\":\"UNIDADES\",\"stock\":20,\"stockSeguridad\":5,\"precioVenta\":98.99,"
				+ "\"precioCompra\":90,\"alto\":0.71,\"ancho\":0.43,\"profundo\":0.48,\"pesoUnitario\":10,"
				+ "\"estanteria\":{\"id\":2,\"categoria\":\"BANOS\",\"capacidad\":550},\"urlimagen\":\"foto.png\"}},"
				+ "{\"id\":8,\"cantidad\":1,\"precio\":399,\"producto\":{\"id\":7,\"nombre\":\"Lavabo Zeus blanco\",\"descripcion\":\"LABA.\","
				+ "\"unidad\":\"UNIDADES\",\"stock\":5,\"stockSeguridad\":2,\"precioVenta\":399.99,\"precioCompra\":360,\"alto\":0.81,\"ancho\":0.12,\"profundo\":0.46,"
				+ "\"pesoUnitario\":11,\"estanteria\":{\"id\":2,\"categoria\":\"BANOS\",\"capacidad\":550},\"urlimagen\":\"foto.png\"}}],"
				+ "\"descripcion\":null,\"original\":null,\"dependiente\":{\"id\":4,\"name\":\"Jose Javier Mu\u00f1oz Jimenez\""
				+ ",\"email\":\"u0kvftny@talk21.com\",\"tlf\":\"688128113\",\"direccion\":\"Calle Via Nueva, 65, 21499, Palos De La Frontera(Huelva)\","
				+ "\"image\":\"foto.png\",\"nss\":\"210473213263\",\"nif\":\"64145337Q\"},\"cliente\":{\"id\":3,\"name\":\"Luis Olivares Dominguez\","
				+ "\"email\":\"rnu6e107@journalism.com\",\"tlf\":\"787983426\",\"direccion\":\"Calle Campo Horno 51, D\u00fadar, Granada, 18355\",\"nif\":\"44812413H\"}},"
				+ "\"vehiculo\":null,\"transportista\":{\"id\":6,\"name\":\"Agustin Pineda Rey\",\"email\":\"f8s9khjgx@btinternet.com\",\"tlf\":\"672910341\","
				+ "\"direccion\":\"Calle Carrera De Espa\u00f1a, 74, 28661, Valdemanco(madrid)\",\"image\":\"foto.png\",\"nss\":\"410473213263\",\"nif\":\"73929968X\"}}";
		String error  = mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/update")
				.with(csrf())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertThat(error).isEqualTo("No puede ser vacio");

	}
	
	
}
