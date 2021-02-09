package org.springframework.gresur.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(	name = "users")
public class User extends BaseEntity {

	@NotBlank(message = "No puede ser vacio")
	@Size(max = 20, message = "Debe ser menor o igual a 20 caracteres")
	@Column(unique = true)
	private String username;


	@NotBlank(message = "No puede ser vacio")
	@Size(max = 120, message = "Debe ser menor o igual a 120 caracteres")
	private String password;
	
	
	@OneToOne(optional = false)
	private Personal personal;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Rol> roles = new HashSet<>();

	public User() {
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	
}
