package com.coworkingspace.backend.security;


import com.coworkingspace.backend.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;

	@Autowired
		private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final UserDetails customerDetails = customerUserDetailsService.loadUserByUsername(authentication.getName());
		if (!passwordEncoder.matches(authentication.getCredentials().toString(), customerDetails.getPassword())) {
			throw new UsernameNotFoundException("Incorrect email or password.");
		}
		return new UsernamePasswordAuthenticationToken(customerDetails.getUsername(), customerDetails.getPassword(), customerDetails.getAuthorities());
	}
}
