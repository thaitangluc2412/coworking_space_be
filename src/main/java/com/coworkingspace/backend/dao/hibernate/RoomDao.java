package com.coworkingspace.backend.dao.hibernate;

import java.util.List;

import com.coworkingspace.backend.dao.entity.Customer;
import com.coworkingspace.backend.dao.entity.Room;
import com.coworkingspace.backend.dto.RoomListDto;
import com.coworkingspace.backend.sdo.CountRoomType;

public interface RoomDao {
	List<Room> getWithFilter(String typeRoomId, String provinceId, String roomName, String cityName, String minPrice, String maxPrice);
	List<CountRoomType> getCountRoomType();
	List<Customer> findAllByBehavior();
}
