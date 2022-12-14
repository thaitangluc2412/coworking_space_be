package com.coworkingspace.backend.common.constant;

public class SecurityConstant {
	private SecurityConstant() {
	}

	public static final String HEADER_NAME = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String LOGIN_PATH = "/api/v1/auth/login";


	public static final String REGISTER_PATH = "/api/v1/customers";
	public static final String RESOURCE_PATH = "/resources/**";

	public static final String ROOM_STATUS = "/api/v1/roomStatuses";
	public static final String ROOM_LIST = "/api/v1/rooms/roomType";
	public static final String ROOM_FAVORITE = "/api/v1/rooms/favorite/*";
	public static final String VERIFICATION_EMAIL = "/api/v1/users/verify";
	public static final String ROOM_TYPE = "*/api/v1/roomTypes";
	public static final String[] PUBLIC_MATCHERS = {LOGIN_PATH, RESOURCE_PATH, VERIFICATION_EMAIL, REGISTER_PATH, ROOM_STATUS, ROOM_LIST, ROOM_TYPE,ROOM_FAVORITE};
}
