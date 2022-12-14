package com.coworkingspace.backend.service;

import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dto.CustomerDto;
import com.coworkingspace.backend.dto.CustomerResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.data.domain.Page;


public interface CustomerService {
	void createCustomer(CustomerDto customerDto);
	List<CustomerResponseDto> getAllCustomers();
	CustomerResponseDto getCurrentUser(HttpServletRequest request);
	void updateCustomer(CustomerDto customerDto);
	int getTotalCustomer();
	Page<CustomerResponseDto> findCustomerPage(int page, int size);
	Page<CustomerResponseDto> findCustomerByCustomerNameContainingOrEmailContaining(String customerName, String email,String phone, int page, int size);
}
