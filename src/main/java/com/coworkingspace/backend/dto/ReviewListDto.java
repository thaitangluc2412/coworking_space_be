package com.coworkingspace.backend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewListDto {
	private String id;
	private String customerName;
	private String roomName;
	private String content;
	private Integer rating;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime timeCreate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime timeUpdate;
}
