package com.coworkingspace.backend.mapper;

import com.coworkingspace.backend.common.utils.ImageStorageUtils;
import com.coworkingspace.backend.dao.entity.Reservation;
import com.coworkingspace.backend.dao.repository.ImageRepository;
import com.coworkingspace.backend.dao.repository.ReservationRepository;
import com.coworkingspace.backend.dto.ImageDto;
import com.coworkingspace.backend.dto.ReservationDto;
import com.coworkingspace.backend.dto.ReservationListDto;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public abstract class ReservationMapperDecorator implements ReservationMapper {
	@Autowired
	@Qualifier("delegate")
	private ReservationMapper delegate;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private ImageMapper imageMapper;

	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public Reservation reservationDtoToReservation(ReservationDto reservationDto) {
		return delegate.reservationDtoToReservation(reservationDto);
	}

	@Override
	public ReservationListDto reservationToReservationListDto(Reservation reservation) {
		ReservationListDto reservationListDto = delegate.reservationToReservationListDto(reservation);
		List<ImageDto> imageDtos = ImageStorageUtils.getImageDtos(
			imageRepository,
			reservation.getRoom().getImageStorage().getId(),
			imageMapper
		);
		reservationListDto.setImages(imageDtos);

		String emailOwner = reservationRepository.getById(reservation.getId()).getRoom().getCustomer().getEmail();
		reservationListDto.setEmailOwner(emailOwner);

		return reservationListDto;
	}
}
