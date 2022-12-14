package com.coworkingspace.backend.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coworkingspace.backend.dao.entity.Behavior;

@Repository
public interface BehaviorRepository extends JpaRepository<Behavior, String> {
	Optional<Behavior> findByCustomerId(String id);
}
