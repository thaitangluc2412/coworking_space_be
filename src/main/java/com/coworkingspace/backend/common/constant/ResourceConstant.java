package com.coworkingspace.backend.common.constant;

import java.nio.file.Path;

public final class ResourceConstant {

	private ResourceConstant() {
	}

	public static final String IMAGES_DIR = "images";
	public static final String TEMP_IMAGES_DIR = "temp_images";

	public static final Path ROOT_URL = Path.of("/resources");


	public static final Path ROOT_PATH = Path.of(System.getProperty("user.home")).resolve("coworking_space");
	public static final Path IMAGES_PATH = ROOT_PATH.resolve(IMAGES_DIR);
	public static final Path TEMP_IMAGES_PATH = ROOT_PATH.resolve(TEMP_IMAGES_DIR);
}
