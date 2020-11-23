package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PersonalTests extends ValidatorTests{
	
	private Personal createSUT(String name, String NIF, String email, String tlf, String direccion, String NSS,
							  String image, Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		List<Notificacion> lenviadas = new ArrayList<Notificacion>();
		List<Notificacion> lrecibidas = new ArrayList<Notificacion>();
		List<Contrato> lcontratos = new ArrayList<Contrato>();
		
		if(noti_enviadas != null && noti_enviadas > 0) {
			Notificacion n1 = new Notificacion();
			Notificacion n2 = new Notificacion();
			lenviadas.add(n1);
			lenviadas.add(n2);
		} if(noti_recibidas != null && noti_recibidas > 0) {
			Notificacion n1 = new Notificacion();
			lrecibidas.add(n1);
		} if(contratos != null && contratos > 0) {
			Contrato c1 = new Contrato();
			lcontratos.add(c1);
		};
		
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
	
	private Transportista createSUTTransportista(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
									Integer noti_enviadas, Integer noti_recibidas, Integer contratos, Integer vehiculos) {
		
		List<Vehiculo> lv = new ArrayList<Vehiculo>();
		Personal p = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		if(vehiculos != null && vehiculos > 0) {
			Vehiculo v1 = new Vehiculo();
			Vehiculo v2 = new Vehiculo();
			lv.add(v1);
			lv.add(v2);
		}
		
		Transportista transportista = new Transportista();
		transportista.setName(p.name);
		transportista.setNIF(p.NIF);
		transportista.setEmail(p.email);
		transportista.setTlf(p.tlf);
		transportista.setDireccion(p.direccion);
		transportista.setNSS(p.NSS);
		transportista.setImage(p.image);
		transportista.setNoti_enviadas(p.noti_enviadas);
		transportista.setNoti_recibidas(p.noti_recibidas);
		transportista.setContratos(p.contratos);
		transportista.setVehiculos(lv);	
		return transportista;
	}
	
	private Dependiente createSUTDependiente(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
											Integer noti_enviadas, Integer noti_recibidas, Integer contratos, Integer emitidas) {
		
		List<FacturaEmitida> le = new ArrayList<FacturaEmitida>();
		Personal p = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		if(emitidas != null && emitidas > 0) {
			FacturaEmitida e1 = new FacturaEmitida();
			FacturaEmitida e2 = new FacturaEmitida();
			le.add(e1);
			le.add(e2);
		}
		
		Dependiente dependiente = new Dependiente();
		dependiente.setName(p.name);
		dependiente.setNIF(p.NIF);
		dependiente.setEmail(p.email);
		dependiente.setTlf(p.tlf);
		dependiente.setDireccion(p.direccion);
		dependiente.setNSS(p.NSS);
		dependiente.setImage(p.image);
		dependiente.setNoti_enviadas(p.noti_enviadas);
		dependiente.setNoti_recibidas(p.noti_recibidas);
		dependiente.setContratos(p.contratos);
		dependiente.setFacturasEmitidas(le);	
		return dependiente;
	}
	
	private EncargadoDeAlmacen createSUTEncargado(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
												Integer noti_enviadas, Integer noti_recibidas, Integer contratos, Integer almacen) {
		
		Almacen alm = null;
		Personal p = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);
		
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
		encargado.setNoti_enviadas(p.noti_enviadas);
		encargado.setNoti_recibidas(p.noti_recibidas);
		encargado.setContratos(p.contratos);
		encargado.setAlmacen(alm);	
		return encargado;
	}
		
	
	/* * * * * * * * * * * * * * * * * * * * * * * * *
	 *  VALIDACIONES PARA LOS ATRIBUTOS DE PERSONAL  *
	 * * * * * * * * * * * * * * * * * * * * * * * * */
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png, 1, 0, 0",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1",
	}) /* Tambien es valido para el administrador, que no tiene atributos extra*/
	void validatePersonalNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
									Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(0);
		
	}
	
	@ParameterizedTest
	@CsvSource({
		"'     ', 12345678A, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"'', 12345678C, email2@email.com, 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1",
		" , 12345678A, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
	})
	void validatePersonalNameNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
										 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

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
		+ "Stuart y Silva Falcó y Gurtubay, 12345678A, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		
		"Ra, 12345678A, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1"
	})
	void validatePersonalNameSizeTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
									 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, '   ', email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"Juan, '', email2@email.com, 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1",
		"Miguel , , email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
	})
	void validatePersonalNIFNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
										 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678Aa, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"Juan, 12, email2@email.com, 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1",
		"Paco , 12345, email@email.com, 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
	})
	void validatePersonalNIFPatternTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
										 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, '    ', 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"Juan, 12345678C, '', 696823445, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1",
		"Paco , 12345678A, , 696823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
	})
	void validatePersonalEmailNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
										 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Email"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, email@email.com, '    ', C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"Juan, 12345678C, email2@email.com, '', C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1",
		"Paco , 12345678A, email@email.com, , C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
	})
	void validatePersonalTlfNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
										 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, email@email.com, 823445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"Juan, 12345678C, email2@email.com, 69682344520020, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1",
		"Paco , 12345678A, email@email.com, 2a1s23445, C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1",
	})
	void validatePersonalTlfPatternTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
										 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, email@email.com, 696823445, '', 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"Juan, 12345678C, email2@email.com, 696823445, '    ', 12 1234567891, /resources/alex.jpeg, , 0, 1",
		"Paco , 12345678A, email@email.com, 696823445, , 12 1234567890, /resources/lucas.png, 0, 1, 1",
	})
	void validatePersonalDireccionNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
										 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Size"));
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Pepe, 12345678A, email@email.com, 696823445, C/, 12 1234567890, /resources/lucas.png, 0, 1, 1",
		"Juan, 12345678C, email2@email.com, 696823445, C/ Semiglorieta candida ruiz de la virgen santisima de las hermanas santas de la santisima concepcion, 12 1234567891, /resources/alex.jpeg, , 0, 1",
		"Paco , 12345678A, email@email.com, 696823445, C/ Este texto tiene exactamente 51 caractere y peta, 12 1234567890, /resources/lucas.png, 0, 1, 1",
	})
	void validatePersonalDireccionSizeTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
										 Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, '    ', /resources/lucas.png, 0, 1, 1",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, '', /resources/paco.png, 1, 0, 0",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, , /resources/alex.jpeg, , 0, 1",
	})
	void validatePersonalNSSNotBlankTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
									Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		constraintViolations.removeIf(x -> x.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName().contains("Pattern"));
		assertThat(constraintViolations.size()).isEqualTo(1);		
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12  1234567890, /resources/lucas.png, 0, 1, 1",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 1212 34a567891, /resources/paco.png, 1, 0, 0",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 12345 as91, /resources/alex.jpeg, , 0, 1",
	})
	void validatePersonalNSSPatternTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
									Integer noti_enviadas, Integer noti_recibidas, Integer contratos) {
		
		Personal personal = this.createSUT(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Personal>> constraintViolations = validator.validate(personal);
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
		
	
	/* * * * * * * * * * * * * * * * * * * * * *
	 * VALIDACIONES ATRIBUTOS TRANSPORTISTA    *
	 * * * * * * * * * * * * * * * * * * * * * */
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1, 1",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png, 1, 0, 0, 1",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1, 1",
	})
	void validateTransportistaNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
									Integer noti_enviadas, Integer noti_recibidas, Integer contratos, Integer vehiculos) {
		
		Transportista transportista = this.createSUTTransportista(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos, vehiculos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Transportista>> constraintViolations = validator.validate(transportista);
		assertThat(constraintViolations.size()).isEqualTo(0);		
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1, ",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png, 1, 0, 0, 0",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1, ",
	})
	void validateTransportistaVehiculosSizeTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
												Integer noti_enviadas, Integer noti_recibidas, Integer contratos, Integer vehiculos) {
		
		Transportista transportista = this.createSUTTransportista(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos, vehiculos);

		Validator validator = createValidator();
		Set<ConstraintViolation<Transportista>> constraintViolations = validator.validate(transportista);
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
	
	/* * * * * * * * * * * * * * * * * * * * * *
	 * VALIDACIONES ATRIBUTOS DEPENDIENTE      *
	 * * * * * * * * * * * * * * * * * * * * * */
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1, 1",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png, 1, 0, 0, 0",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1, ",
	})
	void validateDependienteNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
												Integer noti_enviadas, Integer noti_recibidas, Integer contratos, Integer emitidas) {
		
		Dependiente dependiente = this.createSUTDependiente(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos, emitidas);

		Validator validator = createValidator();
		Set<ConstraintViolation<Dependiente>> constraintViolations = validator.validate(dependiente);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}
	
	/* * * * * * * * * * * * * * * * * * * * * *
	 * VALIDACIONES ATRIBUTOS ENCARGADO        *
	 * * * * * * * * * * * * * * * * * * * * * */
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1, 1",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png, 1, 0, 0, 1",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1, 1",
	})
	void validateEncargadoNoErrorsTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
												Integer noti_enviadas, Integer noti_recibidas, Integer contratos, Integer almacen) {
		
		EncargadoDeAlmacen encargado = this.createSUTEncargado(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos, almacen);

		Validator validator = createValidator();
		Set<ConstraintViolation<EncargadoDeAlmacen>> constraintViolations = validator.validate(encargado);
		assertThat(constraintViolations.size()).isEqualTo(0);	
	}
	
	@ParameterizedTest
	@CsvSource({
		"Antonio, 12345678A, email@email.com, 696823445 , C/ patata, 12 1234567890, /resources/lucas.png, 0, 1, 1, 0",
		"Paco, 12345678B, email1@email.com, 956728496, C/ patata2, 121234567891, /resources/paco.png, 1, 0, 0, 0",
		"Alex, 12345678C, email2@email.com, 716839827, C/ patata4, 12 1234567891, /resources/alex.jpeg, , 0, 1, ",
	})
	void validateEncargadoAlmacenNotNullTest(String name, String NIF, String email, String tlf, String direccion, String NSS, String image, 
												Integer noti_enviadas, Integer noti_recibidas, Integer contratos, Integer almacen) {
		
		EncargadoDeAlmacen encargado = this.createSUTEncargado(name, NIF, email, tlf, direccion, NSS, image, noti_enviadas, noti_recibidas, contratos, almacen);

		Validator validator = createValidator();
		Set<ConstraintViolation<EncargadoDeAlmacen>> constraintViolations = validator.validate(encargado);
		assertThat(constraintViolations.size()).isEqualTo(1);	
	}
}

