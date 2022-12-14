package com.coworkingspace.backend.dao.hibernate;

import java.util.List;

import com.coworkingspace.backend.dao.entity.District;
import com.coworkingspace.backend.dao.entity.Ward;

public interface AddressDao {
	List<District> getDistrict(Integer provinceCode);
	List<Ward> getWard(Integer districtCode);
	Ward getWardById(Integer code);
}
