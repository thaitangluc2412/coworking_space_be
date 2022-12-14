package com.coworkingspace.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageDto {
	private String id;
	private String url;

	private LocalDateTime timeCreate;
	private LocalDateTime timeUpdate;

	public ImageDto(Object o, String url, String fileName) {
		this.id = (String) o;
		this.url = url;
	}
}
