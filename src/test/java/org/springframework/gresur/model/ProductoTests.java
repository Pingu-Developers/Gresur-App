package org.springframework.gresur.model;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductoTests extends ValidatorTests {

	private Producto createSUT(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Estanteria e = null;
		
		if(estanteria!=null && estanteria>0) {
			e = new Estanteria();
		}
				
		Producto producto = new Producto();
		producto.setNombre(nombre);
		producto.setDescripcion(descripcion);
		producto.setUnidad(unidad == null ? null : Unidad.valueOf(unidad.toUpperCase()));
		producto.setStock(stock);
		producto.setStockSeguridad(stockSeguridad);
		producto.setURLImagen(URLImagen);
		producto.setPrecioVenta(precioVenta);
		producto.setPrecioCompra(precioCompra);
		producto.setAlto(alto);
		producto.setAncho(ancho);
		producto.setProfundo(profundo);
		producto.setPesoUnitario(pesoUnitario);
		producto.setEstanteria(e);
		
		return producto;
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 21, 12, 14, 3, 4",
		"Saco de cemento, Buena calidad, kg, 100, 10, sacoCemento.jpg, 20, 10, 10, 12, 14, 7, 2"
	})
	void validateProductoNoErrorsTest(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(0);
	}
	
	@ParameterizedTest
	@CsvSource({
		", Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 21, 12, 14, 3, 4",
		" '  ', Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 21, 12, 14, 3, 4"
	})
	void validateProductoNombreNotBlankTest(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, , 200, 30, azulejoRojo.jpg, 50, 35, 21, 12, 14, 3, 4",
		"Saco de cemento, Buena calidad, , 100, 10, sacoCemento.jpg, 20, 10, 10, 12, 14, 7, 2"
	})
	void validateProductoUnidadNotNullTest(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, -44, 30, azulejoRojo.jpg, 50, 35, 21, 12, 14, 3, 4",
		"Saco de cemento, Buena calidad, kg, -100, 10, sacoCemento.jpg, 20, 10, 21, 12, 14, 7, 2"
	})
	void validateProductoStockMinTest(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 444, -30, azulejoRojo.jpg, 50, 35, 21, 12, 14, 3, 4",
		"Saco de cemento, Buena calidad, kg, 100, -10, sacoCemento.jpg, 20, 10, 21, 12, 14, 7, 2"
	})
	void validateProductoStockSeguridadMinTest(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, -50, 35, 21, 12, 14, 3, 4",
		"Saco de cemento, Buena calidad, kg, 100, 10, sacoCemento.jpg, -20, 10, 21, 12, 14, 7, 2"
	})
	void validateProductoPrecioVentaMinTest(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, -35, 21, 12, 14, 3, 4",
		"Saco de cemento, Buena calidad, kg, 100, 10, sacoCemento.jpg, 20, -10, 21, 12, 14, 7, 2"
	})
	void validateProductoPrecioCompraMinTest(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, , 12, 14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, , 32, 14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, , 12, 34, 3, 4"
	})
	void validateProductoAltoNotNull(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 0, 12, 14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, -100, 12, 14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, -1, 12, 14, 3, 4"
	})
	void validateProductoAltoPositive(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 32, , 14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 12, , 14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 10, , 54, 3, 4"
	})
	void validateProductoAnchoNotNull(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 10, -12, 14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 20, 0, 14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 32, -12, 44, 3, 4"
	})
	void validateProductoAnchoPositive(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 12, 12, , 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 31, 1, , 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 12, 12, , 3, 4"
	})
	void validateProductoProfundoNotNull(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 32, 12, 0, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 30, 32, -14, 3, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 53, 12, -4, 3, 4"
	})
	void validateProductoProfundoPositive(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 21, 12, 14, -44, 4",
		"Azulejo, Es de color rojo, m2, 200, 30, azulejoRojo.jpg, 50, 35, 21, 12, 14, -1, 4"
	})
	void validateProductoPesoUnitarioMinTest(String nombre, String descripcion, String unidad, Integer stock, Integer stockSeguridad, String URLImagen, Double precioVenta, Double precioCompra, Double alto, Double ancho, Double profundo, Double pesoUnitario, Integer estanteria) {
		
		Producto producto = this.createSUT(nombre, descripcion, unidad, stock, stockSeguridad, URLImagen, precioVenta, precioCompra, alto, ancho, profundo, pesoUnitario, estanteria);
				
		Validator validator = createValidator();
		Set<ConstraintViolation<Producto>> constraintViolations = validator.validate(producto);
		
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

}
