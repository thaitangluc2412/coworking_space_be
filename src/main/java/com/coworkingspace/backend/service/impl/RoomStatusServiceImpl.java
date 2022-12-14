package com.coworkingspace.backend.service.impl;

import com.coworkingspace.backend.dao.entity.RoomStatus;
import com.coworkingspace.backend.dao.repository.RoomStatusRepository;
import com.coworkingspace.backend.service.RoomStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomStatusServiceImpl implements RoomStatusService {

	@Autowired
	private RoomStatusRepository roomStatusRepository;

	@Override
	public List<RoomStatus> getAll() {
		return roomStatusRepository.findAll();
	}
}
