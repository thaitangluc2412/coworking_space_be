package com.coworkingspace.backend.common.sdo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class EmailProperty {
	private final Map<String, Object> properties;

	public EmailProperty() {
		properties = new HashMap<>();
	}

	public <T> void setProperty(String propertyName, T value) {
		properties.put(propertyName, value);
	}

	public <T> void setProperty(String propertyName, T oldValue, T newValue) {
		if (!oldValue.equals(newValue)) {
			properties.put(propertyName, List.of(oldValue, newValue));
		}
	}
}
