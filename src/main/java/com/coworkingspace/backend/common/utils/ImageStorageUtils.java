package com.coworkingspace.backend.common.utils;

import com.coworkingspace.backend.dao.entity.Image;
import com.coworkingspace.backend.dao.entity.ImageStorage;
import com.coworkingspace.backend.dao.repository.ImageRepository;
import com.coworkingspace.backend.dto.ImageDto;
import com.coworkingspace.backend.mapper.ImageMapper;

import java.util.List;
import java.util.stream.Collectors;

public final class ImageStorageUtils {
	private ImageStorageUtils() {
	}

	public static ImageStorage createOrUpdateImageStorageWithImages(ImageStorage imageStorage,
	                                                                List<ImageDto> imageDtos,
	                                                                ImageMapper imageMapper) {
		List<Image> images = imageDtos.stream().map(imageMapper::imageDtoToEntity).collect(Collectors.toList());

		if (imageStorage.getId() == null) {
			imageStorage = new ImageStorage();
		}
		images.forEach(imageStorage::addImage);

		return imageStorage;
	}

	public static List<ImageDto> getImageDtos(ImageRepository imageRepository,
	                                          String imageStorageId,
	                                          ImageMapper imageMapper) {
		List<Image> images = imageRepository.getByImageStorageId(imageStorageId);
		return images.stream().map(imageMapper::imageToImageDto).collect(Collectors.toList());
	}
}
