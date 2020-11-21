package org.springframework.gresur.model;

import javax.validation.Validator;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorTests {
	
	 protected Validator createValidator() {
	        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
	        localValidatorFactoryBean.afterPropertiesSet();
	        return localValidatorFactoryBean;
	    }
	

}
