package com.coworkingspace.backend.configuration;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.difflib.text.DiffRowGenerator;

@Configuration
public class BeanConfiguration {

	@Bean
	public Tika tika() {
		return new Tika();
	}

	@Bean
	@Primary
	public DiffRowGenerator diffRowGenerator() {
		return DiffRowGenerator.create()
				.showInlineDiffs(true)
				.mergeOriginalRevised(true)
				.inlineDiffByWord(true)
				.build();
	}
}
