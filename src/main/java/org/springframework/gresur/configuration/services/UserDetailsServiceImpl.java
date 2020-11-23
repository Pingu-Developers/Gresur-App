package org.springframework.gresur.configuration.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.gresur.model.User;
import org.springframework.gresur.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		User user = userRepo.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("User not found:"+username));
		return UserDetailsImpl.build(user);
	}

}
