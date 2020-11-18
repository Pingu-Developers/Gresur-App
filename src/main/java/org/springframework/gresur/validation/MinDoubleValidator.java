package org.springframework.gresur.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinDoubleValidator implements ConstraintValidator<MinDouble, Double>{

	private Double valor;
	
	@Override
	public void initialize(MinDouble param) {
		this.valor = param.value();
	}
	
	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		return valor<=value;
	}
	
}
