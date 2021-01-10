package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PersonalTests extends ValidatorTests{
	
	private Personal createSUT(String name, String NIF, String email, String tlf, String direccion, String NSS,
							  String image) {

		
		Personal personal = new Personal();
		personal.setName(name);
		personal.setNIF(NIF);
		personal.setEmail(email);
		personal.setTlf(tlf);
		personal.setDireccion(direccion);
		personal.setNSS(NSS);
		personal.setImage(image);
		return personal;
	}
	
	private Transportista createSUTTransportista(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal p = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);
		
		Transportista transportista = new Transportista();
		transportista.setName(p.name);
		transportista.setNIF(p.NIF);
		transportista.setEmail(p.email);
		transportista.setTlf(p.tlf);
		transportista.setDireccion(p.direccion);
		transportista.setNSS(p.NSS);
		transportista.setImage(p.image);
		return transportista;
	}
	
	private Dependiente createSUTDependiente(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal p = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);
		
		Dependiente dependiente = new Dependiente();
		dependiente.setName(p.name);
		dependiente.setNIF(p.NIF);
		dependiente.setEmail(p.email);
		dependiente.setTlf(p.tlf);
		dependiente.setDireccion(p.direccion);
		dependiente.setNSS(p.NSS);
		dependiente.setImage(p.image);
		return dependiente;
	}
	
	private EncargadoDeAlmacen createSUTEncargado(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
												 Integer almacen) {
		
		Almacen alm = null;
		Personal p = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);
		
		if(almacen != null && almacen > 0) {
			alm = new Almacen();
		}
		
		EncargadoDeAlmacen encargado = new EncargadoDeAlmacen();
		encargado.setName(p.name);
		encargado.setNIF(p.NIF);
		encargado.setEmail(p.email);
		encargado.setTlf(p.tlf);
		encargado.setDireccion(p.direccion);
		encargado.setNSS(p.NSS);
		encargado.setImage(p.image);
		encargado.setAlmacen(alm);	
		return encargado;
	}
		
	
	/* * * * * * * * * * * * * * * * * * * * * * * * *
	 *  VALIDACIONES PARA LOS ATRIBUTOS DE PERSONAL  *
	 * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg",
	}) /* Tambien es valido para el administrador, que no tiene atributos extra*/
	void validatePersonalNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(0);
		
	}
	
	@ParameterizedTest
	@CsvSource({
		"'     ', 12345678A, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
		"'', 12345678C, email2@email.com, 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg",
		" , 12345678A, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalNameNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"María del Rosario Cayetana Paloma "
		+ "Alfonsa Victoria Eugenia Fernanda "
		+ "Teresa Francisca de Paula Lourdes "
		+ "Antonia Josefa Fausta Rita Castor  "
		+ "Dorotea Santa Esperanza Fitz-James"
		+ "Stuart y Silva Falcó y Gurtubay, 12345678A, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
		
		"Ra, 12345678A, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png"
	})
	void validatePersonalNameSizeTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, '   ', email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
		"Juan, '', email2@email.com, 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg",
		"Miguel , , email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalNIFNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678Aa, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
		"Juan, 12, email2@email.com, 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg",
		"Paco , 12345, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalNIFPatternTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, '    ', 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
		"Juan, 12345678C, '', 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg",
		"Paco , 12345678A, , 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalEmailNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Email"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, asolkdslk, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
		"Juan, 12345678C, allm, 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg",
		"Paco , 12345678A, asaaaaaaaaaaaaaaaaa@, 696823445, C/ patata, 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalEmailEmailTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, email@email.com, '    ', C/ patata, 12 1234567890, /resources/lucas.png",
		"Juan, 12345678C, email2@email.com, '', C/ patata4, 12 1234567891, /resources/alex.jpeg",
		"Paco , 12345678A, email@email.com, , C/ patata, 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalTlfNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, email@email.com, 823445, C/ patata, 12 1234567890, /resources/lucas.png",
		"Juan, 12345678C, email2@email.com, 69682344520020, C/ patata4, 12 1234567891, /resources/alex.jpeg",
		"Paco , 12345678A, email@email.com, 2a1s23445, C/ patata, 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalTlfPatternTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, email@email.com, 696823445, '', 12 1234567890, /resources/lucas.png",
		"Juan, 12345678C, email2@email.com, 696823445, '    ', 12 1234567891, /resources/alex.jpeg",
		"Paco , 12345678A, email@email.com, 696823445, , 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalDireccionNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, email@email.com, 696823445, C/, 12 1234567890, /resources/lucas.png",
		"Juan, 12345678C, email2@email.com, 696823445, C , 12 1234567891, /resources/alex.jpeg",
		"Paco , 12345678A, email@email.com, 696823445, C/ Semiglorieta candida ruiz de la virgen santisima de las hermanas santas de la santisima concepcion, 12 1234567890, /resources/lucas.png",
	})
	void validatePersonalDireccionSizeTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, '    ', /resources/lucas.png",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, '', /resources/paco.png",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, , /resources/alex.jpeg",
	})
	void validatePersonalNSSNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);		
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12  1234567890, /resources/lucas.png",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 1212 34a567891, /resources/paco.png",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 12345 as91, /resources/alex.jpeg",
	})
	void validatePersonalNSSPatternTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
		
	
	/* * * * * * * * * * * * * * * * * * * * * *
	 * VALIDACIONES ATRIBUTOS TRANSPORTISTA    *
	 * * * * * * * * * * * * * * * * * * * * * */
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg",
	})
	void validateTransportistaNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Transportista transportista = this.createSUTTransportista(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Transportista>> constraintViolations = validator.validate(transportista);
		assertThat(constraintViolations.size()).isEqualTo(0);		
	}
	
	/* * * * * * * * * * * * * * * * * * * * * *
	 * VALIDACIONES ATRIBUTOS DEPENDIENTE      *
	 * * * * * * * * * * * * * * * * * * * * * */
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg",
	})
	void validateDependienteNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image) {
		
		Dependiente dependiente = this.createSUTDependiente(name, NIF, email, tlf, direccion, NSS, image);

		Validator validator = createValidator();
		Set<ConstraintViolation<Dependiente>> constraintViolations = validator.validate(dependiente);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}
	
	/* * * * * * * * * * * * * * * * * * * * * *
	 * VALIDACIONES ATRIBUTOS ENCARGADO        *
	 * * * * * * * * * * * * * * * * * * * * * */
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png, 1",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png, 1",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg, 1",
	})
	void validateEncargadoNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, Integer almacen) {
		
		EncargadoDeAlmacen encargado = this.createSUTEncargado(name, NIF, email, tlf, direccion, NSS, image, almacen);

		Validator validator = createValidator();
		Set<ConstraintViolation<EncargadoDeAlmacen>> constraintViolations = validator.validate(encargado);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png, 0",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png, 0",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg, ",
	})
	void validateEncargadoAlmacenNotNullTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, Integer almacen) {
		
		EncargadoDeAlmacen encargado = this.createSUTEncargado(name, NIF, email, tlf, direccion, NSS, image, almacen);

		Validator validator = createValidator();
		Set<ConstraintViolation<EncargadoDeAlmacen>> constraintViolations = validator.validate(encargado);
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
}

