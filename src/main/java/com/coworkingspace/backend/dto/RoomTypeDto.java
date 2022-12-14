package com.coworkingspace.backend.dto;

import com.coworkingspace.backend.dao.entity.ImageStorage;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RoomTypeDto {
	private String id;
	private String imageStorageId;
	private String url;
	private String roomTypeName;
	private String description;
}
