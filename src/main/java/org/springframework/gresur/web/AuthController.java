package org.springframework.gresur.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.gresur.util.Tuple3;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
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
	public ResponseEntity<?> authenticatedUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt,
												userDetails.getId(),
												userDetails.getUsername(),
												roles));
				
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Rol> roles = new HashSet<>();

		if (strRoles == null) {
			Rol userRole = roleRepository.findByName(ERol.ROLE_DEPENDIENTE)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Rol adminRole = roleRepository.findByName(ERol.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "encargado":
					Rol encargadoRole = roleRepository.findByName(ERol.ROLE_ENCARGADO)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(encargadoRole);

					break;
				case "transportista":
					Rol transportistaRole = roleRepository.findByName(ERol.ROLE_TRANSPORTISTA)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(transportistaRole);

					break;
				default:
					Rol userRole = roleRepository.findByName(ERol.ROLE_DEPENDIENTE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
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

}
