package com.coworkingspace.backend.controller;

import com.coworkingspace.backend.dto.LoginRequestDto;
import com.coworkingspace.backend.dto.LoginResponseDto;
import com.coworkingspace.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto loginRequestDto) {
		LoginResponseDto loginResponseDto = loginService.authenticate(loginRequestDto);

		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}

}