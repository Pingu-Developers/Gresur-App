package org.springframework.gresur.validation;

import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.gresur.model.Configuracion;

public class SalarioMinimoValidator implements ConstraintValidator<SalarioMinimo, Double>{
	
//	@Autowired
//	private ConfiguracionService configService;
//	
	@PersistenceContext
	Double salario = Persistence.createEntityManagerFactory("Copnfiguracion").createEntityManager().find(Configuracion.class, 0).getSalarioMinimo();
	
	@Override
	public void initialize(SalarioMinimo constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	
	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		return salario<=value;
	}
	
}
