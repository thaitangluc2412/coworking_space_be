package com.coworkingspace.backend.service.impl;

import com.coworkingspace.backend.common.utils.JwtUtil;
import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dao.entity.Role;
import com.coworkingspace.backend.dao.repository.CustomerRepository;
import com.coworkingspace.backend.dto.CustomerDto;
import com.coworkingspace.backend.dto.CustomerResponseDto;
import com.coworkingspace.backend.mapper.CustomerMapper;
import com.coworkingspace.backend.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import static com.coworkingspace.backend.common.constant.SecurityConstant.HEADER_NAME;
import static com.coworkingspace.backend.common.constant.SecurityConstant.TOKEN_PREFIX;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public void createCustomer(CustomerDto customerDto) {
		customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
		Customer customer = customerMapper.customerDtoToCustomer(customerDto);
		customerRepository.save(customer);
	}

	@Override
	public List<CustomerResponseDto> getAllCustomers() {
		return customerRepository.findAll().stream().map(customer -> customerMapper.customerToCustomerResponseDto(customer)).collect(
			Collectors.toList());
	}

	@Override
	public CustomerResponseDto getCurrentUser(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HEADER_NAME);
		String token = authorizationHeader.substring(TOKEN_PREFIX.length());
		String email = jwtUtil.getUsernameFromToken(token);
		Customer customer = customerRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("Not found email"));
		return customerMapper.customerToCustomerResponseDto(customer);
	}

	@Override public void updateCustomer(CustomerDto customerDto) {
		Customer customer = customerMapper.customerDtoToCustomer(customerDto);
		if (customerDto.getPassword() == null) {
			String password = customerRepository.findByEmail(customerDto.getEmail()).get().getPassword();
			customer.setPassword(password);
		} else {
			String password = passwordEncoder.encode(customerDto.getPassword());
			customer.setPassword(password);
		}
		Role role = customerRepository.findByEmail(customerDto.getEmail()).get().getRole();
		customer.setRole(role);
		customerRepository.save(customer);
	}

	@Override
	public int getTotalCustomer() {
		return customerRepository.getTotalCustomer();
	}

	@Override public Page<CustomerResponseDto> findCustomerPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Customer> customersPage = customerRepository.findAll(pageable);
		return customersPage.map(customer -> customerMapper.customerToCustomerResponseDto(customer));
	}

	@Override
	public Page<CustomerResponseDto> findCustomerByCustomerNameContainingOrEmailContaining(String customerName, String email, String phone, int page,
		int size) {
		return customerRepository.findCustomerByCustomerNameContainingOrEmailContainingOrPhoneNumberContaining(customerName, email, phone,
			PageRequest.of(page, size)).map(customer -> customerMapper.customerToCustomerResponseDto(customer));
	}
}
