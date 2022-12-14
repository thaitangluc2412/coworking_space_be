package com.coworkingspace.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomerUserDetailsService extends UserDetailsService {
	@Override
	UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
