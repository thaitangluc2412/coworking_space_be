package com.coworkingspace.backend.service.impl;

import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dao.repository.CustomerRepository;
import com.coworkingspace.backend.security.CustomerUserDetails;
import com.coworkingspace.backend.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerUserDetailsServiceImpl implements CustomerUserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;


	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<Customer> customer = customerRepository.findByEmail(email);
		if (customer.isEmpty()) {
			throw new BadCredentialsException("Customer not valid");
		}
		return new CustomerUserDetails(customer.get());
	}
}
