package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@MappedSuperclass
public class Entidad{
	
	@Id
	@TableGenerator(
            name = "ENTITY_ID",
            table = "ID_Generator",
            pkColumnName = "name",
            valueColumnName = "sequence",
            allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ENTITY_ID")
	protected Long id;
	
	@Version
	@Column(columnDefinition = "integer DEFAULT 0", nullable = false)
	private Integer version;
	
	@NotBlank(message = "No puede ser vacio")
	@Size(min = 3, max = 50, message = "Debe ser de entre 3 y 50 caracteres")
	@Column(name = "name")
	protected String name;

	@NotBlank(message = "No puede ser vacio")
	@Column(unique=true)
	@Pattern(regexp = "^[0-9]{8}([A-Z]{1})?", message = "NIF no valido")
    protected String NIF;

	@NotBlank(message = "No puede ser vacio")
    @Email(message = "Email no valido")
    protected String email;
    
	@NotBlank(message = "No puede ser vacio")
    @Pattern(regexp = "^[0-9]{9}$", message = "Telefono no valido")
    protected String tlf;
    
	@NotBlank(message = "No puede ser vacio")
    @Size(max=100, min=3, message = "Debe ser de entre 3 y 100 caracteres")
    @Lob
    protected String direccion;
}