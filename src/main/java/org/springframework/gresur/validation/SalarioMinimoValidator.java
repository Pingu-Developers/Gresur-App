package org.springframework.gresur.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.service.ConfiguracionService;

public class SalarioMinimoValidator implements ConstraintValidator<SalarioMinimo, Double>{
	
	@Autowired
	private ConfiguracionService configService;
	
	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		return configService.getSalarioMinimo()<=value;
	}
	
}
