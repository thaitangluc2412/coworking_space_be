package com.coworkingspace.backend.controller;

import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dto.CustomerDto;
import com.coworkingspace.backend.dto.CustomerResponseDto;
import com.coworkingspace.backend.sdo.ObjectSdo;
import com.coworkingspace.backend.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto) {
		customerService.createCustomer(customerDto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// @PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public ResponseEntity<List<CustomerResponseDto>> getAllCustomer() {
		List<CustomerResponseDto> customerResponseDtos = customerService.getAllCustomers();
		return new ResponseEntity<>(customerResponseDtos, HttpStatus.OK);
	}

	@GetMapping("/me")
	public ResponseEntity<CustomerResponseDto> getCurrentUser(HttpServletRequest request) {
		CustomerResponseDto customerResponseDto = customerService.getCurrentUser(request);
		return ResponseEntity.status(HttpStatus.OK).body(customerResponseDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto) {
		customerService.updateCustomer(customerDto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	// Get total customer for admin page
	@GetMapping("/total")
	public ResponseEntity<?> getTotalCustomer() {
		int total = customerService.getTotalCustomer();
		return ResponseEntity.ok(total);
	}

	@GetMapping("/page/list")
	@ResponseBody
	public ResponseEntity<?> findCustomerPage(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
		@RequestParam(name = "size", required = false, defaultValue = "7") int size) {
		Page<CustomerResponseDto> customers = customerService.findCustomerPage(page, size);
		List<CustomerResponseDto> customerList = customers.toList();
		List<Object> cusObjectList = Arrays.asList(customerList.toArray());

		List<CustomerResponseDto> allCustomers = customerService.getAllCustomers();
		int count = allCustomers.size();

		ObjectSdo objectSdo = new ObjectSdo(count, cusObjectList);
		return new ResponseEntity<>(objectSdo, HttpStatus.OK);
	}

	@GetMapping("/searchAll/list")
	@ResponseBody
	public ResponseEntity<?> findCustomerByCustomerNameContainingOrEmailContaining(
		@RequestParam(value = "value", required = false) String value,
		@RequestParam(name = "page", required = false, defaultValue = "0") int page,
		@RequestParam(name = "size", required = false, defaultValue = "7") int size
	) {

		if (Objects.equals(value, "")) {
			Page<CustomerResponseDto> customers = customerService.findCustomerPage(page, size);
			List<CustomerResponseDto> customerList = customers.toList();
			List<Object> cusObjectList = Arrays.asList(customerList.toArray());

			List<CustomerResponseDto> allCustomers = customerService.getAllCustomers();
			int count = allCustomers.size();

			ObjectSdo objectSdo = new ObjectSdo(count, cusObjectList);
			return new ResponseEntity<>(objectSdo, HttpStatus.OK);
		}
		Page<CustomerResponseDto> customerPage = customerService.findCustomerByCustomerNameContainingOrEmailContaining(value, value, value, page, size);
		List<CustomerResponseDto> customers = customerPage.toList();
		int cnt = customers.size();
		return new ResponseEntity<>(new ObjectSdo(cnt, new ArrayList(customers)), HttpStatus.OK);

	}
}
