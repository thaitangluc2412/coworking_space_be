package com.coworkingspace.backend.mapper;

import com.coworkingspace.backend.dao.entity.Image;
import com.coworkingspace.backend.dto.ImageDto;
import org.mapstruct.Mapper;

@Mapper
public interface ImageMapper {

	Image imageDtoToEntity(ImageDto imageDto);

	ImageDto imageToImageDto(Image image);

}
