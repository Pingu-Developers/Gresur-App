package org.springframework.gresur.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.AdministradorService;
import org.springframework.gresur.service.AlmacenService;
import org.springframework.gresur.service.ClienteService;
import org.springframework.gresur.service.ConfiguracionService;
import org.springframework.gresur.service.ContratoService;
import org.springframework.gresur.service.DependienteService;
import org.springframework.gresur.service.EncargadoDeAlmacenService;
import org.springframework.gresur.service.EstanteriaService;
import org.springframework.gresur.service.FacturaEmitidaService;
import org.springframework.gresur.service.FacturaRecibidaService;
import org.springframework.gresur.service.LineasEnviadoService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.service.PedidoService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DBUtility {
	@Autowired
	private ConfiguracionService configService;
	@Autowired
	private VehiculoService vehiculoService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private LineasFacturaService lfService;
	@Autowired
	private FacturaEmitidaService facturaEmitidaService;
	@Autowired
	private FacturaRecibidaService facturaRecibidaService;
	@Autowired
	private LineasEnviadoService lnService;
	@Autowired
	private NotificacionService notificacionService;
	@Autowired
	private ContratoService contratoService;
	@Autowired
	private TransportistaService transportistaService;
	@Autowired
	private DependienteService dependienteService;
	@Autowired
	private EncargadoDeAlmacenService encargadoService;
	@Autowired
	private AdministradorService adminService;
	@Autowired
	private ProveedorService proveedorService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private EstanteriaService estanteriaService;
	@Autowired
	private AlmacenService almacenService;
	@Autowired
	public DBUtility() {
		
	}
	
	@Transactional
	public void clearDB() {
		configService.deleteAll();
		pedidoService.deleteAll();
		vehiculoService.deleteAll();
		lfService.deleteAll();
		facturaEmitidaService.deleteAll();
		facturaRecibidaService.deleteAll();
		lnService.deleteAll();
		notificacionService.deleteAll();
		contratoService.deleteAll();
		transportistaService.deleteAll();
		dependienteService.deleteAll();
		encargadoService.deleteAll();
		adminService.deleteAll();
		proveedorService.deleteAll();
		clienteService.deleteAll();
		productoService.deleteAll();
		estanteriaService.deleteAll();
		almacenService.deleteAll();	
	}
}
