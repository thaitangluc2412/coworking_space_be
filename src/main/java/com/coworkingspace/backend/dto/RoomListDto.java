package com.coworkingspace.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RoomListDto {
	private String id;
	private Double dayPrice;
	private String roomTypeId;
	private String roomTypeName;
	private String roomName;
	private Double averageRating;
	private String address;
	private String city;
	private String description;
	private List<ImageDto> images;
	private List<UtilityDto> utilities;
	private LocalDateTime timeCreate;
	private LocalDateTime timeUpdate;
}
