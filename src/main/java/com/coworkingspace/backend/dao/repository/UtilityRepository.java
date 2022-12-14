package com.coworkingspace.backend.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coworkingspace.backend.dao.entity.Utility;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, String> {
	List<Utility> getByUtilityStorageId(String id);
}
