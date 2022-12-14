package com.coworkingspace.backend.mapper;

import com.coworkingspace.backend.common.utils.ImageStorageUtils;
import com.coworkingspace.backend.common.utils.PriceUtils;
import com.coworkingspace.backend.common.utils.UtilityStorageUtils;
import com.coworkingspace.backend.dao.entity.ImageStorage;
import com.coworkingspace.backend.dao.entity.Price;
import com.coworkingspace.backend.dao.entity.Province;
import com.coworkingspace.backend.dao.entity.Room;
import com.coworkingspace.backend.dao.entity.UtilityStorage;
import com.coworkingspace.backend.dao.repository.ImageRepository;
import com.coworkingspace.backend.dao.repository.ProvinceRepository;
import com.coworkingspace.backend.dao.repository.UtilityRepository;
import com.coworkingspace.backend.dto.ImageDto;
import com.coworkingspace.backend.dto.PriceDto;
import com.coworkingspace.backend.dto.RoomCreateDto;
import com.coworkingspace.backend.dto.RoomListDto;
import com.coworkingspace.backend.dto.UtilityDto;
import com.coworkingspace.backend.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.List;

public abstract class RoomMapperDecorator implements RoomMapper {
	@Autowired
	@Qualifier("delegate")
	private RoomMapper delegate;

	@Autowired
	private ImageMapper imageMapper;

	@Autowired
	private UtilityMapper utilityMapper;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private UtilityRepository utilityRepository;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Override
	public Room roomCreateDtoToRoom(RoomCreateDto roomCreateDto) throws NotFoundException {
		Room room = delegate.roomCreateDtoToRoom(roomCreateDto);
		Price price = PriceUtils.createOrUpdatePrice(
				room.getPrice(),
				new PriceDto(
						roomCreateDto.getId(),
						roomCreateDto.getDayPrice(),
						roomCreateDto.getMonthPrice(),
						roomCreateDto.getYearPrice()
				)
		);

		room.setPrice(price);
		ImageStorage imageStorage = ImageStorageUtils.createOrUpdateImageStorageWithImages(
				room.getImageStorage(),
				roomCreateDto.getImages(),
				imageMapper
		);
		room.setImageStorage(imageStorage);

		UtilityStorage utilityStorage = UtilityStorageUtils.createOrUpdateUtilityStorageWithUtilities(
			room.getUtilityStorage(),
			roomCreateDto.getUtilities(),
			utilityMapper
		);
		room.setUtilityStorage(utilityStorage);
		return room;
	}

	@Override
	public RoomCreateDto roomToRoomCreateDto(Room room) throws NotFoundException {
		RoomCreateDto roomCreateDto = delegate.roomToRoomCreateDto(room);

		List<ImageDto> imageDtos = ImageStorageUtils.getImageDtos(
				imageRepository,
				roomCreateDto.getImageStorageId(),
				imageMapper
		);
		roomCreateDto.setImages(imageDtos);

		List<UtilityDto> utilityDtos = UtilityStorageUtils.getUtilityDtos(
			utilityRepository,
			roomCreateDto.getUtilityStorageId(),
			utilityMapper
		);
		roomCreateDto.setUtilities(utilityDtos);

		roomCreateDto.setDayPrice(room.getPrice().getDayPrice());
		roomCreateDto.setMonthPrice(room.getPrice().getMonthPrice());
		roomCreateDto.setYearPrice(room.getPrice().getYearPrice());
		return roomCreateDto;
	}

	@Override
	public RoomListDto roomToRoomListDto(Room room){
		RoomListDto roomListDto = delegate.roomToRoomListDto(room);
		List<ImageDto> imageDtos = ImageStorageUtils.getImageDtos(
				imageRepository,
				room.getImageStorage().getId(),
				imageMapper
		);
		roomListDto.setImages(imageDtos);

		List<UtilityDto> utilityDtos = UtilityStorageUtils.getUtilityDtos(
			utilityRepository,
			room.getUtilityStorage().getId(),
			utilityMapper
		);
		roomListDto.setUtilities(utilityDtos);

		Province province = provinceRepository.findByCode(room.getProvince().getCode()).get();
		roomListDto.setCity(province.getName());

		return roomListDto;
	}
}
