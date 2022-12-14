package com.coworkingspace.backend.mapper;

import com.coworkingspace.backend.dao.entity.Room;
import com.coworkingspace.backend.dao.entity.RoomType;
import com.coworkingspace.backend.dto.RoomDto;
import com.coworkingspace.backend.dto.RoomTypeDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RoomTypeMapper {

	@Mapping(source = "imageStorageId", target = "imageStorage.id")
	RoomType roomTypeDtoToRoomType(RoomTypeDto roomTypeDto);

	@InheritInverseConfiguration(name = "roomTypeDtoToRoomType")
	RoomTypeDto roomTypeToRoomTypeDto(RoomType roomType);
}
