package com.coworkingspace.backend.dao.repository;

import com.coworkingspace.backend.dao.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, String> {
	Optional<Price> findById(String id);
}
