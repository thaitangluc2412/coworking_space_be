package com.coworkingspace.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coworkingspace.backend.dao.entity.District;
import com.coworkingspace.backend.dao.entity.Province;
import com.coworkingspace.backend.dao.entity.Ward;
import com.coworkingspace.backend.service.AddressService;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@GetMapping("/provinces")
	public ResponseEntity<List<Province>> getAllProvinces() {
		List<Province> provinces = addressService.getAllProvince();
		return new ResponseEntity<>(provinces, HttpStatus.OK);
	}

	@GetMapping("/districts/{code}")
	public ResponseEntity<List<District>> getByProvinceCode(@PathVariable Integer code) {
		List<District> districts = addressService.getByProvinceCode(code);
		return new ResponseEntity<>(districts, HttpStatus.OK);
	}

	@GetMapping("/wards/{code}")
	public ResponseEntity<List<Ward>> getByDistrictCode(@PathVariable Integer code) {
		List<Ward> districts = addressService.getByDistrictCode(code);
		return new ResponseEntity<>(districts, HttpStatus.OK);
	}

	@GetMapping("/wards/get-by-ward-id/{code}")
	public ResponseEntity<Ward> getByWardCode(@PathVariable Integer code) {
		Ward ward = addressService.getByWardId(code);
		return new ResponseEntity<>(ward, HttpStatus.OK);
	}
}
