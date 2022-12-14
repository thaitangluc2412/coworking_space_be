package com.coworkingspace.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PriceDto {
	private String id;
	private Double dayPrice;
	private Double monthPrice;
	private Double yearPrice;
	private LocalDateTime timeCreate;
	private LocalDateTime timeUpdate;

	public PriceDto(String id, Double dayPrice, Double monthPrice, Double yearPrice) {
		this.id = id;
		this.dayPrice = dayPrice;
		this.monthPrice = monthPrice;
		this.yearPrice = yearPrice;
	}
}
