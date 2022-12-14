package com.coworkingspace.backend.dao.repository;

import com.coworkingspace.backend.dao.entity.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
	Optional<Customer> findByEmail(String email);

	@Query("select customer from Customer customer join customer.behaviors bi where bi.time > 0")
	List<Customer> findAllByBehavior();

	// TODO: Thêm khoảng thời gian nếu muốn thống kê theo tháng
	@Query(value = "SELECT count(customer_id) as total FROM customer", nativeQuery = true)
	int getTotalCustomer();
	Page<Customer> findCustomerByCustomerNameContainingOrEmailContainingOrPhoneNumberContaining(String customerName, String email, String phone, Pageable pageable);
}
