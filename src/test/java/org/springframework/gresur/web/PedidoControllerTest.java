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
	
	//Datos a Testear
	private static final Integer PAGE = 0;
	private static final Integer SIZE = 7;
	private static final String ORDEN = "";
	private static final Long ID_PEDIDO = 2L;
	private static final String ESTADO = "PREPARADO";
	

	
	//Pedidos Para Hoy (Transportistas)
	private List<Tuple5<Long, String, EstadoPedido, String, String>> pedidosDeHoy;
	private Tuple5<Long, String, EstadoPedido, String, String> tuplaPedidoDeHoy;
	private Vehiculo vehiculo;

	private Pedido pedido;
	private Transportista transportista;
	private Almacen almacen;
	private Estanteria estanteria;
	private Producto p1;
	private Producto p2;
	private FacturaEmitida fe;
	private Cliente cliente;
	private Dependiente dependiente;
	private LineaFactura lf1;
	private LineaFactura lf2;
	private List<LineaFactura> lf;
	
	@BeforeEach
	void setUp() throws Exception {


		pedidosDeHoy = new ArrayList<Tuple5<Long,String,EstadoPedido,String,String>>();
		tuplaPedidoDeHoy = new Tuple5<Long, String, EstadoPedido, String, String>();		
		vehiculo = new Vehiculo();	
		
		almacen = new Almacen();
		estanteria = new Estanteria();	
		p1 = new Producto();
		
		p2 = new Producto();		
		pedido = new Pedido();		
		transportista = new Transportista();	
	
		fe = new FacturaEmitida();	
		cliente = new Cliente();
		dependiente = new Dependiente();	
		
		lf = new ArrayList<LineaFactura>();	
		lf1 = new LineaFactura();
		lf2 = new LineaFactura();
		
	
	}

	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	PAGE PEDIDO								                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Pagina Pedido")
    @Test
	void testGetPagePedidosisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/page={pageNo}&size={pageSize}&order={orden}",PAGE,SIZE,ORDEN) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 PEDIDO	POR ID							                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Pedido Por Id")
    @Test
	void testGetPedidosByIdisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/id/{id}",ID_PEDIDO) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 PEDIDO	POR ESTADO ORDENADO							                     *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Pedido Por Estado Ordenado")
    @Test
	void testGetPedidosByEstadoisOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/{estado}/page={pageNo}&size={pageSize}&order={orden}",ESTADO,PAGE,SIZE,ORDEN) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST	AÑADIR NUEVO  PEDIDO								                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Agregar Nuevo Pedido")
    @Test
    void testPostAddPedidosisOk() throws Exception  {
		
		//Creacion JSON
		String json = "{\"e1\":\"Calle Larios n25\",\"e2\":\"PREPARADO\","
				+ "\"e3\":\"2020-10-22\",\"e4\":{\"e1\":2500,\"e2\":false,"
				+ "\"e3\":\"20070284E\",\"e4\":[{\"e1\":1,\"e2\":25}]}}";
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/pedido/add") 
				.with(csrf())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									POST	CANCELAR   PEDIDO								                     *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Cancelar Pedido Por Id -- caso positivo")
    @Test
	void testPostCancelarPedidoByIdisOk() throws Exception  {
		
		//Devuelve Un Pedido Para Cualquier ID
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		
		//Devuelve Pedido Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.pedidoService.save(any(Pedido.class))).willReturn(new Pedido());
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/pedido/{id}",ID_PEDIDO) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Cancelar Pedido Por Id -- caso negativo")
    @Test
	void testPostCancelarPedidoByIdError() throws Exception  {
		
		//Devuelve Pedido Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.pedidoService.save(any(Pedido.class))).willReturn(new Pedido());
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/pedido/{id}",ID_PEDIDO) 
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	OCUPACION   PEDIDOS DEL DIA								                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Ver Ocupacion Pedidos Del Dia")
    @Test
	void testPostOcupacionDelDiaisOk() throws Exception  {
		
		//Peticion POST
		mockMvc.perform(MockMvcRequestBuilders.
				post("/api/pedido/ocupacion")
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 PEDIDOS DEL DIA TRANSPORTISTA								             *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Ver Pedidos Del Dia Asociados Al Transportista")
    @Test
	void testGetPedidosTransportistaHoyIsOk() throws Exception  {
		
		//Creacion Pedidos Hoy
		tuplaPedidoDeHoy.setE1(1L);
		tuplaPedidoDeHoy.setE2("Calle Alambique");
		tuplaPedidoDeHoy.setE3(EstadoPedido.PREPARADO);
		tuplaPedidoDeHoy.setE4("Paco Manuel Lopez");
		tuplaPedidoDeHoy.setE5("Camion");
		pedidosDeHoy.add(tuplaPedidoDeHoy);
		
		//Devuelve Un Pedido Para Cualquier ID
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(pedidosDeHoy);
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/hoy")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET	 FACTURA DE UN PEDIDO								                     *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Ver Pedidos Del Dia Asociados Al Transportista")
    @Test
	void testGetFacturaDePedidoIsOk() throws Exception  {
		
		//Devuelve Un Pedido Para Cualquier ID
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/factura/{id}",ID_PEDIDO)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT SET EN REPARTO								                             *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Poner En Reparto Un Pedido -- caso positivo")
    @Test
    @Disabled
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este test esta comentado, ya que debido a que se utiliza la clase Authentication siempre devuelve un 200 OK   *
	 * por lo que no podemos probar correctamente el funcionamiento en este caso, pero la logica es correcta		 *																								
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	void testPutSetEnRepartoPedidoIsOk() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(2000.);
		vehiculo.setImagen("furgoneta.png");
		vehiculo.setMatricula("3090GND");
		vehiculo.setMMA(200.);
		vehiculo.setTipoVehiculo(TipoVehiculo.FURGONETA);
		
		//Devuelve Un Pedido Para Cualquier ID
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		
		//Devuelve Pedido Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.pedidoService.save(any(Pedido.class))).willReturn(new Pedido());
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		
		//Peticion PUT
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/reparto/{id}",ID_PEDIDO)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Poner En Reparto Un Pedido - caso negativo")
    @Test
	void testPutSetEnRepartoPedidoError() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(2000.);
		vehiculo.setImagen("furgoneta.png");
		vehiculo.setMatricula("3090GND");
		vehiculo.setMMA(200.);
		vehiculo.setTipoVehiculo(TipoVehiculo.FURGONETA);
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		
		//Peticion PUT
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/reparto/{id}",ID_PEDIDO)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT SET EN ENTREGADO								                         *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Poner En Entregado Un Pedido -- caso positivo")
    @Test
    @Disabled
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Este test esta comentado, ya que debido a que se utiliza la clase Authentication siempre devuelve un 200 OK   *
	 * por lo que no podemos probar correctamente el funcionamiento en este caso, pero la logica es correcta		 *																								
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	void testPutSetEnEntregadoPedidoIsOk() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(2000.);
		vehiculo.setImagen("furgoneta.png");
		vehiculo.setMatricula("3090GND");
		vehiculo.setMMA(200.);
		vehiculo.setTipoVehiculo(TipoVehiculo.FURGONETA);
		
		//Devuelve Un Pedido Para Cualquier ID
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();	
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		
		//Peticion put
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/entregado/{id}",ID_PEDIDO)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	@WithMockUser(value = "spring")
	@DisplayName("Poner En Entregado Un Pedido -- caso negativo")
    @Test
	void testPutSetEnEntregadoPedidoError() throws Exception  {
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(2000.);
		vehiculo.setImagen("furgoneta.png");
		vehiculo.setMatricula("3090GND");
		vehiculo.setMMA(200.);
		vehiculo.setTipoVehiculo(TipoVehiculo.FURGONETA);
		
		//Conversion Objeto a JSON
		Gson gson  = new Gson();
		String jsonString =  gson.toJson(vehiculo).replace("MMA", "mma");
		
		//Peticion put
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/entregado/{id}",ID_PEDIDO)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									GET PEDIDO POR ESTADO Y TRANSPORTISTA								         *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Obtener Pedido Por Estado y Transportista")
    @Test
	void testGetPedidoPorEstadoTransportistaIsOk() throws Exception  {
		
		//Peticion GET
		mockMvc.perform(MockMvcRequestBuilders.
				get("/api/pedido/transportista/{estado}",ESTADO)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT SET ESTA PAGADO								                             *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Poner Pagado  Factura De Un Pedido -- caso positivo")
    @Test
	void testPutSetPagadoFacturaPedidoIsOk() throws Exception  {
		
		//Creacion Almacen
		almacen.setCapacidad(1500.00);
		almacen.setDireccion("C/ Almacen");	
		
		//Creacion Estanteria
		estanteria.setAlmacen(almacen);
		estanteria.setCapacidad(550.00);
		estanteria.setCategoria(Categoria.AZULEJOS);
		
		//Creacion Producto N1
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
			
		//Creacion Producto N2
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
		
		//Creacion Vehiculo
		vehiculo.setCapacidad(2000.);
		vehiculo.setImagen("furgoneta.png");
		vehiculo.setMatricula("3090GND");
		vehiculo.setMMA(200.);
		vehiculo.setTipoVehiculo(TipoVehiculo.FURGONETA);
		
		//Creacion Pedido
		pedido.setDireccionEnvio("Calle España");
		pedido.setEstado(EstadoPedido.ENTREGADO);
		pedido.setFechaEnvio(LocalDate.of(2020, 10, 22));
		pedido.setFechaRealizacion(LocalDate.of(2020, 10, 10));
		pedido.setVehiculo(vehiculo);
		
		//Creacion Transportista
		transportista.setName("Antonio Manuel Lopez");
		transportista.setDireccion("Calle Andalucia");
		transportista.setEmail("antonio@gmail.com");
		transportista.setNIF("30070284X");
		transportista.setNSS("123456789966");
		transportista.setTlf("278675482");
		pedido.setTransportista(transportista);
		
		//Creacion Cliente
		cliente.setDireccion("Calle");
		cliente.setEmail("cliente@mail.es");
		cliente.setName("Jose Luis");
		cliente.setNIF("54789663T");
		cliente.setTlf("657412397");
		fe.setCliente(cliente);

		//Creacion Dependiente
		dependiente.setDireccion("C/ ejemplo");
		dependiente.setEmail("patata@gmail.com");
		dependiente.setImage("/resourdes/foto.png");
		dependiente.setName("Antonio Jose Ruiz Ruiz");
		dependiente.setNIF("12345678L");
		dependiente.setNSS("123456789012");
		dependiente.setTlf("123456789");
		
		//Creacion Factura Emitida
		fe.setDependiente(dependiente);
		fe.setDescripcion("Todo OK Jose Luis");
		fe.setEstaPagada(true);
		fe.setImporte(1500.);
		fe.setNumFactura("E5");
		
		//Creacion Linea Factura 1
		lf1.setCantidad(10);
		lf1.setFactura(fe);
		lf1.setPrecio(100.15);
		lf1.setProducto(p1);
		//Creacion Linea Factura 2
		lf2.setCantidad(20);
		lf2.setFactura(fe);
		lf2.setPrecio(220.00);
		lf2.setProducto(p2);
		fe.setLineasFacturas(lf);
		
		//Devuelve Un Pedido Para Cualquier ID
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		
		//Peticion PUT
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/pagado/{id}",pedido.getId())
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@DisplayName("Poner Pagado  Factura De Un Pedido -- caso negativo")
    @Test
	void testsPutSetPagadoPedidoError() throws Exception  {
		
		//Peticion PUT
		mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/pagado/{id}",ID_PEDIDO)
				.with(csrf())
				).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * 									PUT UPDATE PEDIDO							                                 *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	@WithMockUser(value = "spring")
	@DisplayName("Actualizar Datos Pedido -- caso positivo")
    @Test
	void testPutUpdatePedidoisOk() throws Exception  {
		
		//Creacion JSON
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
		
		//Devuelve Un Pedido Para Cualquier ID
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
		
		//Devuelve Pedido Cuando Se Guarda, Evitando Objetos Nulos Para El Log
		given(this.pedidoService.save(any(Pedido.class))).willReturn(new Pedido());
		
		//Peticion PUT
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
		
		//Creacion JSON
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
		
		//Devuelve Un Pedido Para Cualquier ID
		given(this.pedidoService.findByID(any(Long.class))).willReturn(new Pedido());
					
		//Peticion PUT
		String error  = mockMvc.perform(MockMvcRequestBuilders.
				put("/api/pedido/update")
				.with(csrf())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getContentAsString();
		
		//Comprobamos Mensaje Error Es El Esperado
		assertThat(error).isEqualTo("No puede ser vacio");

	}
	
	
}
