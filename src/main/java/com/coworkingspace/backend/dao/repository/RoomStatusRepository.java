package com.coworkingspace.backend.dao.repository;

import com.coworkingspace.backend.dao.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomStatusRepository extends JpaRepository<RoomStatus, String> {
}
