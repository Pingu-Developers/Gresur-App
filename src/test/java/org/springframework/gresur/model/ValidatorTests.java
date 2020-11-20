package org.springframework.gresur.model;

import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorTests {
	
	public static Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean =
		new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
		}
	

}
