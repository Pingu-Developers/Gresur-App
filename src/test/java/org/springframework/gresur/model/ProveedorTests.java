package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProveedorTests extends ValidatorTests {

	private Proveedor createSUT(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
			
		
		Proveedor proveedor = new Proveedor();
		proveedor.setName(name);
		proveedor.setNIF(NIF);
		proveedor.setTlf(tlf);
		proveedor.setEmail(email);
		proveedor.setDireccion(direccion);		
		proveedor.setIBAN(IBAN);

		
		return proveedor;
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, manolo@correo.es, 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"Wos, 96658774Y, ja@correo.es, 987654321, Cadiz, ES6621000418401234567891"
	})
	void validateProveedorNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}

	@ParameterizedTest
	@CsvSource({
		", 98856332T, manolo@correo.es, 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"'   ', 96658774Y, ja@correo.es, 987654321, Cadiz, ES6621000418401234567891"
	})
	void validateProveedorNameNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);

		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"WW, 98856332T, manolo@correo.es, 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"FOSDNFSNDIFNSDIFNSDUIFNSDUFNSUIDNFSUDINFSUFNSUNFSUDNFSUIDNFUDSNFUNSDIUFNSDUNFSDUNFSDIU, 96658774Y, ja@correo.es, 987654321, Cadiz, ES6621000418401234567891"
		})
	void validateProveedorNameSizeTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);

		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, , manolo@correo.es, 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"Wos, '   ', ja@correo.es, 987654321, Cadiz, ES6621000418401234567891"
	})
	void validateProveedorNIFNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, FF44343, manolo@correo.es, 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"Wos, 4242342FB, ja@correo.es, 987654321, Cadiz, ES6621000418401234567891"
	})
	void validateProveedorNIFPatternTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, , 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"Wos, 96658774Y, '   ', 987654321, Cadiz, ES6621000418401234567891"
	})
	void validateProveedorEmailNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Email"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, manolo.correo.es, 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"Wos, 96658774Y, ja_correo.es, 987654321, Cadiz, ES6621000418401234567891"
	})
	void validateProveedorEmailEmailTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, manolo@correo.es, , Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"Wos, 96658774Y, ja@correo.es, '   ', Cadiz, ES6621000418401234567891"
	})
	void validateProveedorTlfNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, manolo@correo.es, 2324234234234234324, Aljamir Software S.L. Glorieta Fernando Quiñones T, ES6621000418401234567891",
		"Wos, 96658774Y, ja@correo.es, 432423, Cadiz, ES6621000418401234567891"
	})
	void validateProveedorTlfPatternTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, manolo@correo.es, 123456789, , ES6621000418401234567891",
		"Wos, 96658774Y, ja@correo.es, 987654321, '     ', ES6621000418401234567891"
	})
	void validateProveedorDireccionNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);

		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, manolo@correo.es, 123456789, Up, ES6621000418401234567891",
		"Wos, 96658774Y, ja@correo.es, 987654321, NFSDIFNSDIFNSIFNSIFNSDUIFNSDUIFNSDIUNFSUDNFUSDNFSUDNFUSDNFUSDNFIUSDNFUNSDFUNSDUFNSDNUFDDDD, ES6621000418401234567891"
	})
	void validateProveedorDireccionSizeTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, manolo@correo.es, 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, ",
		"Wos, 96658774Y, ja@correo.es, 987654321, Cadiz, '   '"
	})
	void validateProveedorIBANNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
	
	@ParameterizedTest
	@CsvSource({
		"Alberto, 98856332T, manolo@correo.es, 123456789, Aljamir Software S.L. Glorieta Fernando Quiñones T, 662424234es2342341000418401234567891",
		"Wos, 96658774Y, ja@correo.es, 987654321, Cadiz, 5453534543_FFESS"
	})
	void validateProveedorIBANPatternTest(String name, String NIF, String email, String tlf, String direccion, String IBAN) {
		Proveedor proveedor = this.createSUT(name, NIF, email, tlf, direccion, IBAN);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Proveedor>> constraintViolations = validator.validate(proveedor);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("NotBlank"));
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
	
}