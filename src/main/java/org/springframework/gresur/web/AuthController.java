package org.springframework.gresur.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.configuration.jwt.JwtUtils;
import org.springframework.gresur.configuration.services.UserDetailsImpl;
import org.springframework.gresur.model.ERol;
import org.springframework.gresur.model.Personal;
import org.springframework.gresur.model.Rol;
import org.springframework.gresur.model.User;
import org.springframework.gresur.model.userPayload.JwtResponse;
import org.springframework.gresur.model.userPayload.LoginRequest;
import org.springframework.gresur.model.userPayload.MessageResponse;
import org.springframework.gresur.model.userPayload.SignupRequest;
import org.springframework.gresur.repository.RolRepository;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.gresur.util.Tuple2;
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RolRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticatedUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		
		if(result.hasErrors()) {
			List<FieldError> le = result.getFieldErrors();
			return ResponseEntity.badRequest().body(le.get(0).getDefaultMessage() + (le.size()>1? " (Total de errores: " + le.size() + ")" : ""));
		}
		
		else {
			UserDetailsImpl userDetails = null;
			String jwt = null;
			try {
				
				Authentication authentication = authManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				jwt = jwtUtils.generateJwtToken(authentication);
				userDetails = (UserDetailsImpl) authentication.getPrincipal();
				
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e);
			}
			
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());
			log.info("/auth/signin User : "+userDetails.getUsername()+" has logged in");
			return ResponseEntity.ok(new JwtResponse(jwt,
													userDetails.getId(),
													userDetails.getUsername(),
													roles));
		}	
	}
	
	
	@GetMapping("/user")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> getPersonData(){
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		Tuple3<String,List<String>,Personal> res = new Tuple3<>();
		res.name1 = "username";
		res.setE1(userDetails.getUsername());
		
		res.name2 = "roles";
		res.setE2(roles);
		
		Personal per = userRepository.findByUsername(userDetails.getUsername()).orElse(null).getPersonal();
		
		res.name3 ="personal";
		res.setE3(per);
		
		return ResponseEntity.ok(res);
	}
	
	@PutMapping("/password")
	@PreAuthorize("permitAll()")
	public ResponseEntity<?> newPassword(@RequestBody Tuple2<String, String> pwds) {
		Authentication user = SecurityContextHolder.getContext().getAuthentication();
		User empleado = userRepository.findByUsername(user.getName()).orElse(null);
		UserDetailsImpl userDetails = (UserDetailsImpl) user.getPrincipal();
		if(user!=null&&encoder.matches(pwds.getE1(), userDetails.getPassword())) {
			empleado.setPassword(encoder.encode(pwds.getE2()));
			
			try {
				userRepository.save(empleado);
				log.info("/auth/password User with id: "+empleado.getId()+" change password successfully");
				return ResponseEntity.ok("Contraseña cambiada");
			}catch(Exception e) {
				log.error("/auth/password " + e.getMessage());
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		
		else {
			return ResponseEntity.badRequest().body("Wrong Password");
		}
	}
}