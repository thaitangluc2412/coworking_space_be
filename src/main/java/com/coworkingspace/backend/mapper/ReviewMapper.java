package com.coworkingspace.backend.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.coworkingspace.backend.dao.entity.Review;
import com.coworkingspace.backend.dto.ReviewDto;
import com.coworkingspace.backend.dto.ReviewListDto;

@Mapper
public interface ReviewMapper {

	@Mapping(source = "customerId", target = "customer.id")
	@Mapping(source = "roomId", target = "room.id")
	Review reviewDtoToReview(ReviewDto reviewDto);

	@InheritInverseConfiguration(name = "reviewDtoToReview")
	ReviewDto reviewToReviewDto(Review review);



	@InheritInverseConfiguration(name = "reviewToReviewListDto")
	Review reviewListDtoToReview(ReviewListDto reviewListDto);

	@Mapping(source = "customer.customerName", target = "customerName")
	@Mapping(source = "room.roomName", target = "roomName")
	@Mapping(source = "timeCreate", target = "timeCreate", dateFormat = "yyyy-MM-dd HH:mm")
	ReviewListDto reviewToReviewListDto(Review review);
}
