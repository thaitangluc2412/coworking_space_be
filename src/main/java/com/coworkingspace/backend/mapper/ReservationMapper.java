package com.coworkingspace.backend.mapper;

import com.coworkingspace.backend.dao.entity.Reservation;
import com.coworkingspace.backend.dto.ReservationDto;
import com.coworkingspace.backend.dto.ReservationListDto;

import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@DecoratedWith(ReservationMapperDecorator.class)
public interface ReservationMapper {

	@Mapping(source = "roomId", target = "room.id")
	@Mapping(source = "customerId", target = "customer.id")
	@Mapping(source = "reservationStatusId", target = "reservationStatus.id")
	@Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd")
	@Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd")
	Reservation reservationDtoToReservation(ReservationDto reservationDto);

	@InheritInverseConfiguration(name = "reservationDtoToReservation")
	ReservationDto reservationToReservationDto(Reservation reservation);

	@InheritInverseConfiguration(name = "reservationToReservationListDto")
	Reservation reservationListDtoToReservation(ReservationListDto reservationListDto);

	@Mapping(source = "reservationStatus.reservationStatusName", target = "reservationStatusName")
	@Mapping(source = "customer.customerName", target = "customerName")
	@Mapping(source = "customer.email", target = "email")
	@Mapping(source = "customer.phoneNumber", target = "phoneNumber")
	@Mapping(source = "room.id", target = "roomId")
	@Mapping(source = "room.roomName", target = "roomName")
	@Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd")
	@Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd")
	ReservationListDto reservationToReservationListDto(Reservation reservation);
}
