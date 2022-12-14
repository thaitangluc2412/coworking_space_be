package com.coworkingspace.backend.controller;

import com.coworkingspace.backend.dao.hibernate.RoomDao;
import com.coworkingspace.backend.dao.repository.RoomRepository;
import com.coworkingspace.backend.dto.RoomCreateDto;
import com.coworkingspace.backend.dto.RoomListDto;
import com.coworkingspace.backend.mapper.RoomMapper;
import com.coworkingspace.backend.sdo.CountRoomType;
import com.coworkingspace.backend.sdo.ObjectSdo;
import com.coworkingspace.backend.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private RoomMapper roomMapper;

	@Autowired
	private RoomDao roomDao;

	@PostMapping
	public ResponseEntity<RoomCreateDto> createRoom(@RequestPart RoomCreateDto roomCreateDto,
		@RequestPart(value = "files", required = false) MultipartFile[] files) {
		roomService.createRoom(roomCreateDto, files);
		return new ResponseEntity<>(roomCreateDto, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<RoomCreateDto>> getAllRoom() {
		List<RoomCreateDto> rooms = roomService.getAll();
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RoomCreateDto> updateRoom(@PathVariable String id,
		@RequestPart RoomCreateDto roomCreateDto,
		@RequestPart( required = false) MultipartFile[] files) throws NotFoundException {
		RoomCreateDto roomCreateDto1 = roomService.updateRoom(id, roomCreateDto, files);
		return ResponseEntity.status(HttpStatus.OK).body(roomCreateDto1);
	}

	@GetMapping("/{roomId}")
	public ResponseEntity<RoomCreateDto> getDetail(@PathVariable String roomId, @RequestParam(required = false) String customerId) throws NotFoundException {
		RoomCreateDto roomListDto = roomService.findByRoomId(roomId, customerId);
		return ResponseEntity.status(HttpStatus.OK).body(roomListDto);
	}

	@GetMapping("/roomFilter")
	public ResponseEntity<List<RoomListDto>> getWithFilter(@RequestParam(required = false) String typeRoomId,
		@RequestParam(required = false) String provinceId, @RequestParam(required = false) String roomName,
		@RequestParam(required = false) String cityName,
		@RequestParam(required = false) String minPrice, @RequestParam(required = false) String maxPrice) {
		List<RoomListDto> rooms = roomService.getWithFilter(typeRoomId, provinceId, roomName, cityName, minPrice, maxPrice);
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@GetMapping("/getByCustomerId/{id}")
	public ResponseEntity<List<RoomListDto>> getByCustomerId(@PathVariable String id) {
		List<RoomListDto> rooms = roomService.findByCustomerId(id);
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteRoom(@PathVariable String id) throws NotFoundException {
		roomService.deleteRoom(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/favorite/customer/{id}")
	public ResponseEntity<List<RoomListDto>> favorite(@PathVariable(required = false) String id){
		return new ResponseEntity<>(roomService.favoriteRoom(id), HttpStatus.OK);
	}

	@GetMapping("/favorite/customer/getAll/{id}")
	public ResponseEntity<List<RoomListDto>> getAllTest(@PathVariable String id){
		System.out.println("id: " + id);
		List<RoomListDto> roomListDtos = roomRepository.findTop6ByOrderByAverageRatingDesc().stream().map(room -> roomMapper.roomToRoomListDto(room)).collect(Collectors.toList());
		return new ResponseEntity<>(roomListDtos, HttpStatus.OK);
	}

	// get total room for admin page
	@GetMapping("/total")
	public ResponseEntity<?> getTotalRoom() {
		int total = (int) roomRepository.count();
		return ResponseEntity.ok(total);
	}

	@GetMapping("/countRoomType")
	public ResponseEntity<?> countRoomType() {
		List<CountRoomType> countRoomTypes = roomDao.getCountRoomType();
		return ResponseEntity.ok(countRoomTypes);
	}

	@GetMapping("/page/list")
	@ResponseBody
	public ResponseEntity<?> findByRoomName(
		@RequestParam(value = "value", required = false) String value,
		@RequestParam(name = "page", required = false, defaultValue = "0") int page,
		@RequestParam(name = "size", required = false, defaultValue = "7") int size
	) {

		if (Objects.equals(value, "")) {
			Page<RoomListDto> rooms = roomService.findRoomPage(page, size);
			List<RoomListDto> roomList = rooms.toList();
			List<Object> cusObjectList = Arrays.asList(roomList.toArray());

			List<RoomCreateDto> allRooms = roomService.getAll();
			int count = allRooms.size();

			ObjectSdo objectSdo = new ObjectSdo(count, cusObjectList);
			return new ResponseEntity<>(objectSdo, HttpStatus.OK);
		}
		Page<RoomListDto> roomPage = roomService.findRoomByRoomName(value, page, size);
		List<RoomListDto> rooms = roomPage.toList();
		int cnt = rooms.size();
		return new ResponseEntity<>(new ObjectSdo(cnt, new ArrayList(rooms)), HttpStatus.OK);
	}
}
