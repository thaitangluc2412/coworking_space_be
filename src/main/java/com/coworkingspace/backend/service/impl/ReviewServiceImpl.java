package com.coworkingspace.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coworkingspace.backend.dao.entity.Review;
import com.coworkingspace.backend.dao.entity.Room;
import com.coworkingspace.backend.dao.repository.ReviewRepository;
import com.coworkingspace.backend.dao.repository.RoomRepository;
import com.coworkingspace.backend.dto.ReviewDto;
import com.coworkingspace.backend.dto.ReviewListDto;
import com.coworkingspace.backend.mapper.ReviewMapper;
import com.coworkingspace.backend.service.ReviewService;
import com.coworkingspace.backend.service.RoomService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ReviewMapper reviewMapper;

	@Autowired
	private RoomService roomService;

	@Autowired
	private RoomRepository roomRepository;

	@Override public ReviewDto createReview(ReviewDto reviewDto) {
		reviewRepository.save(reviewMapper.reviewDtoToReview(reviewDto));
		Room room = roomService.findById(reviewDto.getRoomId());
		List<Review> reviewList = reviewRepository.findByRoomIdOrderByTimeCreate(reviewDto.getRoomId());
		if (!reviewList.isEmpty()){
			Double sum = reviewList.stream().map(review -> review.getRating()).reduce(0.0, Double::sum);
			double avg = sum / reviewList.size();
			room.setAverageRating( Math.round(avg * 2) / 2.0);
			roomRepository.save(room);
		}
		return reviewDto;
	}

	@Override public List<ReviewListDto> findByRoomId(String roomId) {
		return reviewRepository.findByRoomIdOrderByTimeCreate(roomId).stream().map(review -> reviewMapper.reviewToReviewListDto(review)).collect(Collectors.toList());
	}
}
