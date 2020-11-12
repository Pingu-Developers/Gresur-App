package org.springframework.gresur.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@MappedSuperclass
public class Entidad extends NamedEntity{
	
	@NotBlank
	@Column(unique=true)
    protected String NIF;

    @NotBlank
    @Email
    protected String email;
    
    @Pattern(regexp = "^[0-9]{9}$")
    protected String tlf;
    
    @NotBlank
    @Size(max=50, min=3)
    protected String direccion;
}