package com.coworkingspace.backend.service;

import java.util.List;

import com.coworkingspace.backend.dao.entity.District;
import com.coworkingspace.backend.dao.entity.Province;
import com.coworkingspace.backend.dao.entity.Ward;

public interface AddressService {
	List<Province> getAllProvince();
	List<District> getByProvinceCode(Integer code);
	List<Ward> getByDistrictCode(Integer code);
	Province findByCode(Integer code);
	Ward getByWardId(Integer code);
}
