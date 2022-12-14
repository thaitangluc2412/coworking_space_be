package com.coworkingspace.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationListDto {
	private String id;
	private String reservationStatusName;
	private String customerName;
	private String email;
	private String emailOwner;
	private String phoneNumber;
	private String roomId;
	private String roomName;
	private Double total;
	private Boolean reviewed;
	private String startDate;
	private String endDate;
	private List<ImageDto> images;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime timeCreate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime timeUpdate;
}
