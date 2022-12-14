package com.coworkingspace.backend.common.sdo;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Email {
	private String to;
	private List<String> cc;
	private Map<String, Object> properties;
	private String subject;
	private String template;
}

