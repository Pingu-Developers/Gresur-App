package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
public class Entidad{
    
    @Id
    @JsonProperty("nif")
    protected String NIF;
    
    @NotBlank
    @JsonProperty("nombre")
    protected String nombre;
    
    @NotBlank
    @JsonProperty("email")
    protected String email;
    
    //@Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-s./0-9]$")
    @JsonProperty("tlf")
    protected String tlf;
    
    @NotBlank
    @JsonProperty("direccion")
    protected String direccion;
}