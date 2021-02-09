package org.springframework.gresur.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.gresur.model.Contrato;

public interface ContratoRepository extends CrudRepository<Contrato, Long>{
	
	List<Contrato> findAll();
	
	@Query(value = "SELECT C.* FROM CONTRATOS C RIGHT JOIN (SELECT PERSONAL_ID PID, MAX(FECHA_FIN) LAST FROM CONTRATOS GROUP BY PERSONAL_ID) ON PID = PERSONAL_ID WHERE LAST = FECHA_FIN", nativeQuery = true)
	List<Contrato> findLastContratoAllEmpleados();
	
	@Query(value = "SELECT CON.* FROM (SELECT C.* FROM CONTRATOS C RIGHT JOIN (SELECT PERSONAL_ID PID, MAX(FECHA_FIN) LAST FROM CONTRATOS GROUP BY PERSONAL_ID) ON PID = PERSONAL_ID WHERE LAST = FECHA_FIN) CON RIGHT JOIN TRANSPORTISTAS T ON T.ID = CON.ID", nativeQuery = true)
	List<Contrato> findLastContratoTransportista();
	
	@Query(value = "SELECT CON.* FROM (SELECT C.* FROM CONTRATOS C RIGHT JOIN (SELECT PERSONAL_ID PID, MAX(FECHA_FIN) LAST FROM CONTRATOS GROUP BY PERSONAL_ID) ON PID = PERSONAL_ID WHERE LAST = FECHA_FIN) CON RIGHT JOIN DEPENDIENTES D ON D.ID = CON.ID", nativeQuery = true)
	List<Contrato> findLastContratoDependiente();
	
	@Query(value = "SELECT CON.* FROM (SELECT C.* FROM CONTRATOS C RIGHT JOIN (SELECT PERSONAL_ID PID, MAX(FECHA_FIN) LAST FROM CONTRATOS GROUP BY PERSONAL_ID) ON PID = PERSONAL_ID WHERE LAST = FECHA_FIN) CON RIGHT JOIN ENCARGADOS_ALMACEN EA ON EA.ID = CON.ID", nativeQuery = true)
	List<Contrato> findLastContratoEncargado();
	
	@Query(value = "SELECT CON.* FROM (SELECT C.* FROM CONTRATOS C RIGHT JOIN (SELECT PERSONAL_ID PID, MAX(FECHA_FIN) LAST FROM CONTRATOS GROUP BY PERSONAL_ID) ON PID = PERSONAL_ID WHERE LAST = FECHA_FIN) CON RIGHT JOIN ADMINISTRADORES A ON A.ID = CON.ID", nativeQuery = true)
	List<Contrato> findLastContratoAdministrador();
	
	void deleteByPersonalNIF(String NIF);
	
	Optional<Contrato>  findByPersonalNIF(String NIF);
}
