package com.coworkingspace.backend.service.impl;

import com.coworkingspace.backend.dao.repository.RoomTypeRepository;
import com.coworkingspace.backend.dto.RoomTypeDto;
import com.coworkingspace.backend.mapper.RoomTypeMapper;
import com.coworkingspace.backend.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

	@Autowired
	private RoomTypeRepository roomTypeRepository;

	@Autowired
	private RoomTypeMapper roomTypeMapper;

	@Override
	public List<RoomTypeDto> getAll() {
		return roomTypeRepository.findAll().stream().map(roomType -> roomTypeMapper.roomTypeToRoomTypeDto(roomType)).collect(
				Collectors.toList());
	}
}
