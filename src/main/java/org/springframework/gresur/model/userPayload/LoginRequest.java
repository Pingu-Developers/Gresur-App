package org.springframework.gresur.model.userPayload;

import lombok.Data;

@Data
public class LoginRequest {

	private String username;
	
	private String password;
}
