package com.coworkingspace.backend.mapper;

import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dto.CustomerDto;
import com.coworkingspace.backend.dto.CustomerResponseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {

	@Mapping(source = "roleId", target = "role.id")
	Customer customerDtoToCustomer(CustomerDto customerDto);

	@InheritInverseConfiguration(name = "customerDtoToCustomer")
	CustomerDto customerToCustomerDto(Customer customer);

	@Mapping(source = "roleName", target = "role.roleName")
	Customer customerResponseDtoToCustomer(CustomerResponseDto customerResponseDto);

	@InheritInverseConfiguration(name = "customerResponseDtoToCustomer")
	CustomerResponseDto customerToCustomerResponseDto(Customer customer);
}
