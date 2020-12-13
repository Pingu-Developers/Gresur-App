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
import org.springframework.gresur.service.ITVService;
import org.springframework.gresur.service.LineasEnviadoService;
import org.springframework.gresur.service.LineasFacturaService;
import org.springframework.gresur.service.NotificacionService;
import org.springframework.gresur.service.PedidoService;
import org.springframework.gresur.service.ProductoService;
import org.springframework.gresur.service.ProveedorService;
import org.springframework.gresur.service.ReparacionService;
import org.springframework.gresur.service.SeguroService;
import org.springframework.gresur.service.TransportistaService;
import org.springframework.gresur.service.VehiculoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DBUtility {
	@Autowired
	public ConfiguracionService configService;
	@Autowired
	private SeguroService seguroService;
	@Autowired
	private ITVService ITVService;
	@Autowired
	private ReparacionService reparacionService;
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
	
	private DBUtility() {
		
	}
	
	@Transactional
	public static void clearDB() {
		DBUtility util = new DBUtility();
		util.configService.deleteAll();
		util.seguroService.deleteAll();
		util.ITVService.deleteAll();
		util.reparacionService.deleteAll();
		util.vehiculoService.deleteAll();
		util.pedidoService.deleteAll();
		util.lfService.deleteAll();
		util.facturaEmitidaService.deleteAll();
		util.facturaRecibidaService.deleteAll();
		util.lnService.deleteAll();
		util.notificacionService.deleteAll();
		util.contratoService.deleteAll();
		util.transportistaService.deleteAll();
		util.dependienteService.deleteAll();
		util.encargadoService.deleteAll();
		util.adminService.deleteAll();
		util.proveedorService.deleteAll();
		util.clienteService.deleteAll();
		util.productoService.deleteAll();
		util.estanteriaService.deleteAll();
		util.almacenService.deleteAll();	
	}
}
