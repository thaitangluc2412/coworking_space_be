package com.coworkingspace.backend.controller;

import com.coworkingspace.backend.dao.entity.RoomStatus;
import com.coworkingspace.backend.dto.RoomTypeDto;
import com.coworkingspace.backend.service.RoomStatusService;
import com.coworkingspace.backend.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roomTypes")
public class RoomTypeController {
	@Autowired
	private RoomTypeService roomTypeService;

	@GetMapping
	public ResponseEntity<List<RoomTypeDto>> getAllRoomTypes() {
		List<RoomTypeDto> roomTypeDtos = roomTypeService.getAll();
		return new ResponseEntity<>(roomTypeDtos, HttpStatus.OK);
	}

}
