package com.coworkingspace.backend.common.utils;

import java.util.List;
import java.util.stream.Collectors;

import com.coworkingspace.backend.dao.entity.Utility;
import com.coworkingspace.backend.dao.entity.UtilityStorage;
import com.coworkingspace.backend.dao.repository.UtilityRepository;
import com.coworkingspace.backend.dto.UtilityDto;
import com.coworkingspace.backend.mapper.UtilityMapper;

public class UtilityStorageUtils {
	private UtilityStorageUtils() {
	}

	public static UtilityStorage createOrUpdateUtilityStorageWithUtilities(UtilityStorage utilityStorage,
		List<UtilityDto> utilityDtos,
		UtilityMapper utilityMapper) {
		List<Utility> utilities = utilityDtos.stream().map(utilityMapper::utilityDtoToUtility).collect(Collectors.toList());

		if (utilityStorage.getId() == null) {
			utilityStorage = new UtilityStorage();
		}
		utilities.forEach(utilityStorage::addUtility);

		return utilityStorage;
	}

	public static List<UtilityDto> getUtilityDtos(UtilityRepository utilityRepository,
		String utilityStorageId,
		UtilityMapper utilityMapper) {
		List<Utility> utilities = utilityRepository.getByUtilityStorageId(utilityStorageId);
		return utilities.stream().map(utilityMapper::utilityToUtilityDto).collect(Collectors.toList());
	}
}
