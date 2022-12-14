package com.coworkingspace.backend.dao.repository;

import com.coworkingspace.backend.dao.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, String> {
}
