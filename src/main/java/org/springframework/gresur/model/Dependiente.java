package org.springframework.gresur.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dependientes")
public class Dependiente extends Personal{

}
