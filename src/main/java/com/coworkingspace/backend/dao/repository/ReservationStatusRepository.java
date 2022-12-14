package com.coworkingspace.backend.dao.repository;

import com.coworkingspace.backend.dao.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, String> {
	Optional<ReservationStatus> findByReservationStatusName(String name);
}
