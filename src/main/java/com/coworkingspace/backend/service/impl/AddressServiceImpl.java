package com.coworkingspace.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coworkingspace.backend.dao.entity.District;
import com.coworkingspace.backend.dao.entity.Province;
import com.coworkingspace.backend.dao.entity.Ward;
import com.coworkingspace.backend.dao.hibernate.AddressDao;
import com.coworkingspace.backend.dao.repository.ProvinceRepository;
import com.coworkingspace.backend.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private ProvinceRepository provinceRepository;

	@Override public List<Province> getAllProvince() {
		return provinceRepository.findAll();
	}

	@Override public List<District> getByProvinceCode(Integer code) {
		return addressDao.getDistrict(code);
	}

	@Override public List<Ward> getByDistrictCode(Integer code) {
		return addressDao.getWard(code);
	}

	@Override public Province findByCode(Integer code) {
		return provinceRepository.findByCode(code).orElseThrow(() -> new RuntimeException("No province code"));
	}

	@Override public Ward getByWardId(Integer code) {
		return addressDao.getWardById(code);
	}
}
