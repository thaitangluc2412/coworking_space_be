package com.coworkingspace.backend.mapper;

import org.mapstruct.Mapper;

import com.coworkingspace.backend.dao.entity.Utility;
import com.coworkingspace.backend.dto.UtilityDto;

@Mapper
public interface UtilityMapper {

	Utility utilityDtoToUtility(UtilityDto utilityDto);

	UtilityDto utilityToUtilityDto(Utility utility);
}
