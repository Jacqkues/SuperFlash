package fr.cytech.superflash.security;

import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user != null) {
			UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).roles("USER").build();
			 //return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword());
			return userDetails;
		}else {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
	}
	
}