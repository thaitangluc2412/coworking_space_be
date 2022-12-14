package com.coworkingspace.backend.service;

import com.coworkingspace.backend.dao.entity.RoomStatus;

import java.util.List;

public interface RoomStatusService {
	List<RoomStatus> getAll();
}
