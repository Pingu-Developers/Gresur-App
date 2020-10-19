package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {
		  
		  List<Person> persons = new ArrayList<>();
		  Person caraticar = new Person();
		  caraticar.setFirstName("Carlos");
		  caraticar.setLastName("Atienza");
		  persons.add(caraticar);
		  
		  Person tomgalbar = new Person();
		  tomgalbar.setFirstName("Tomás");
		  tomgalbar.setLastName("Galera");
		  persons.add(tomgalbar);
		  
		  Person lucperrui = new Person();
		  lucperrui.setFirstName("Lucas");
		  lucperrui.setLastName("Pérez");
		  persons.add(lucperrui);
		  
		  Person borrondom = new Person();
		  borrondom.setFirstName("Borja");
		  borrondom.setLastName("Rondán");
		  persons.add(borrondom);
		  
		  Person alesancor1 = new Person();
		  alesancor1.setFirstName("Alejandro");
		  alesancor1.setLastName("Santisteban");
		  persons.add(alesancor1);
		  
		  model.put("persons", persons);
		  model.put("title", "Azulejos Gresur");
		  model.put("group", "G3-12");

	    return "welcome";
	  }
}
