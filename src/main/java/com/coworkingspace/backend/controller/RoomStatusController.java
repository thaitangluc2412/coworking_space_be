package com.coworkingspace.backend.controller;

import com.coworkingspace.backend.dao.entity.RoomStatus;
import com.coworkingspace.backend.dto.CustomerResponseDto;
import com.coworkingspace.backend.service.RoomStatusService;
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
@RequestMapping("/api/v1/roomStatuses")
public class RoomStatusController {

	@Autowired
	private RoomStatusService roomStatusService;

	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@GetMapping
	public ResponseEntity<List<RoomStatus>> getAllCustomer() {
		List<RoomStatus> roomStatuses = roomStatusService.getAll();
		return new ResponseEntity<>(roomStatuses, HttpStatus.OK);
	}
}
