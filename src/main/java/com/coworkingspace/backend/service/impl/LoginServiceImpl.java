package com.coworkingspace.backend.service.impl;

import com.coworkingspace.backend.common.utils.JwtUtil;
import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dao.repository.CustomerRepository;
import com.coworkingspace.backend.dto.CustomerResponseDto;
import com.coworkingspace.backend.dto.LoginRequestDto;
import com.coworkingspace.backend.dto.LoginResponseDto;
import com.coworkingspace.backend.mapper.CustomerMapper;
import com.coworkingspace.backend.security.CustomAuthenticationManager;
import com.coworkingspace.backend.service.CustomerUserDetailsService;
import com.coworkingspace.backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

	private final CustomAuthenticationManager customAuthenticationManager;

	private final CustomerUserDetailsService customerUserDetailsService;

	private final JwtUtil jwtUtil;

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	@Override
	public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
		try {
			customAuthenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequestDto.getEmail().trim(),
							loginRequestDto.getPassword()
					)

			);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect email or password.", e);
		}

		// If authenticate successfully, retrieve logged-in user info and generate token to send back
		final UserDetails userDetails = customerUserDetailsService.loadUserByUsername(loginRequestDto.getEmail()
				                                                                              .trim());
		final String token = jwtUtil.generateToken(userDetails);

		final Customer customer = customerRepository.findByEmail(loginRequestDto.getEmail().trim())
				.orElseThrow(() -> new BadCredentialsException("Incorrect email or password."));
		CustomerResponseDto customerResponseDto = customerMapper.customerToCustomerResponseDto(customer);
		return new LoginResponseDto(token, customerResponseDto);
	}
}
