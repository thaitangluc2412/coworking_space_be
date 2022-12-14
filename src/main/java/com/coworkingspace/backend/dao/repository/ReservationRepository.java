package com.coworkingspace.backend.dao.repository;

import java.util.List;

import com.coworkingspace.backend.dao.entity.Reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
	List<Reservation> getByCustomerIdOrderByTimeCreateDesc(String id);
	Reservation getById(String id);

	// TODO: change reservation_status_id
	@Query(value = "select IFNULL(sum(total), 0) as total\n" +
		"from reservation \n" +
		"where reservation_status_id = 'tngpi7zVKfwAY0N';", nativeQuery = true)
	double getProfit();
	Page<Reservation> findReservationByReservationStatusReservationStatusNameContaining(String reservationStatusName, Pageable pageable);
}
