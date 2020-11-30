package org.springframework.gresur.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.gresur.model.Cliente;
import org.springframework.gresur.model.FacturaEmitida;
import org.springframework.gresur.repository.FacturaEmitidaRepository;
import org.springframework.gresur.service.exceptions.ClienteDefaulterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaEmitidaService extends FacturaService<FacturaEmitida, FacturaEmitidaRepository>{
	
	@Autowired
	public FacturaEmitidaService(FacturaEmitidaRepository feRepo) {
		super.facturaRepo = feRepo;
	}
	
	@Transactional(rollbackFor = ClienteDefaulterException.class)
	public FacturaEmitida save(FacturaEmitida emitida) throws DataAccessException,ClienteDefaulterException {
		Cliente cliente = emitida.getCliente();
		if(!facturaRepo.findByClienteIdAndEstaPagadaFalse(cliente.getId()).isEmpty())
			throw new ClienteDefaulterException("El cliente tiene facturas pendientes");
		
		return facturaRepo.save(emitida);
	}
}