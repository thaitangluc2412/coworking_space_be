package com.coworkingspace.backend.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coworkingspace.backend.dao.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
	Optional<Province> findByCode(Integer code);
}
