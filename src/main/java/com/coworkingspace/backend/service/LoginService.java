package com.coworkingspace.backend.service;

import com.coworkingspace.backend.dto.LoginRequestDto;
import com.coworkingspace.backend.dto.LoginResponseDto;

public interface LoginService {
	LoginResponseDto authenticate(LoginRequestDto loginRequestDto);
}
