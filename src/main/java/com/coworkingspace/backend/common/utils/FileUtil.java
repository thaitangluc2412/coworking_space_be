package com.coworkingspace.backend.common.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import org.apache.tika.Tika;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

public final class FileUtil {
	private FileUtil() {
	}

	private static final String[] VALID_FILE_TYPES = {"image/jpg", "image/jpeg", "image/png", "image/bmp"};
	private static final long MAX_BYTE = 5242880;

	/**
	 * Check image is valid or not
	 *
	 * @param tika to detect real file type
	 * @param file input file
	 */
	public static void checkValid(Tika tika, MultipartFile file) throws IOException {
		if (file.getSize() > MAX_BYTE) {
			throw new MaxUploadSizeExceededException(MAX_BYTE);
		}

		String fileName = file.getOriginalFilename();
		if (StringUtils.getFilenameExtension(fileName) == null) {
			throw new RuntimeException();
		}

		String fileExtension = file.getContentType();
		if (!Arrays.asList(VALID_FILE_TYPES).contains(fileExtension)) {
			throw new RuntimeException();
		}

		String fileType = tika.detect(file.getBytes());
		if (fileType.equals(fileExtension)) {
			if (Arrays.asList(VALID_FILE_TYPES).contains(fileType)) {
				return;
			}
			throw new RuntimeException();
		}

		throw new RuntimeException();
	}

	public static String sanitizePathToString(Path path) {
		return path.toString().replace("\\", "/");
	}
}
