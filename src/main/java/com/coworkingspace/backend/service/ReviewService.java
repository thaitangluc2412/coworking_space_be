package com.coworkingspace.backend.service;

import java.util.List;

import com.coworkingspace.backend.dto.ReviewDto;
import com.coworkingspace.backend.dto.ReviewListDto;

public interface ReviewService {
	ReviewDto createReview(ReviewDto reviewDto);
	List<ReviewListDto> findByRoomId(String roomId);
}
