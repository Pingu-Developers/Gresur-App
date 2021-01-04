package org.springframework.gresur.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.service.ClienteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("api/cliente")
public class ClienteController {

	private final ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping
	public Iterable<Cliente> getClientes(){
		return clienteService.findAll();
	}
	
	@PostMapping
	public Cliente add(Cliente cliente) throws DataAccessException{
		return clienteService.save(cliente);
	}
	
	@GetMapping("/{NIF}/isDefaulter")
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public Boolean findClienteIsDefaulterByNIF(@PathVariable("NIF") String NIF) throws DataAccessException{
		Cliente cliente = clienteService.findByNIF(NIF);
		if(cliente == null) {
			throw new IllegalArgumentException("El NIF no es válido o no existe nadie con ese NIF en la base de datos");
		}else {
			return clienteService.isDefaulter(cliente);
		}
	}
	
	@GetMapping("/{NIF}")
	@PreAuthorize("hasRole('DEPENDIENTE')")
	public Cliente findClienteByNIF(@PathVariable("NIF") String NIF) throws DataAccessException{
		return clienteService.findByNIF(NIF);
	}
	
}
