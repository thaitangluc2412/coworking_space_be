package com.coworkingspace.backend.service;

import com.coworkingspace.backend.dao.entity.Room;
import com.coworkingspace.backend.dto.CustomerResponseDto;
import com.coworkingspace.backend.dto.ImageDto;
import com.coworkingspace.backend.dto.RoomCreateDto;
import com.coworkingspace.backend.dto.RoomListDto;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {
	void createRoom(RoomCreateDto roomCreateDto,  MultipartFile[] files);
	List<RoomCreateDto> getAll();
	Room findById(String id);
	RoomCreateDto updateRoom(String id, RoomCreateDto roomCreateDto, MultipartFile[] files) throws NotFoundException;
	List<RoomListDto> getWithFilter(String typeRoomId, String provinceId, String roomName, String cityName, String minPrice, String maxPrice);
	RoomCreateDto findByRoomId(String roomId, String customerId) throws NotFoundException;
	List<RoomListDto> findByCustomerId(String id);
	void deleteRoom(String id);
	List<RoomListDto> favoriteRoom(String id);
	Page<RoomListDto> findRoomPage(int page, int size);
	Page<RoomListDto> findRoomByRoomName(String roomName, int page, int size);
}
