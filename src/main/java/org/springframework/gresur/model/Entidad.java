package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
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
	
	@NotBlank
	@Size(min = 3, max = 50)
	@Column(name = "name")
	protected String name;

	@NotBlank
	@Column(unique=true)
	@Pattern(regexp = "^[0-9]{8}([A-Z]{1})?")
    protected String NIF;

    @NotBlank
    @Email
    protected String email;
    
    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$")
    protected String tlf;
    
    @NotBlank
    @Size(max=100, min=3)
    @Lob
    protected String direccion;
}