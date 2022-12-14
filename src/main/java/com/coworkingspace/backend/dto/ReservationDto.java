package com.coworkingspace.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDto {
	private String id;
	private String roomId;
	private String customerId;
	private String reservationStatusId;
	private String startDate;
	private String endDate;
	private Double total;
	private Double deposit;
	private LocalDateTime timeCreate;
	private LocalDateTime timeUpdate;
}
