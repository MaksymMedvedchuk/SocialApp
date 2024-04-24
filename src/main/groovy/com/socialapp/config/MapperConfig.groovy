package com.socialapp.config

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfig {

	@Bean
	ModelMapper mapper() {
		new ModelMapper()
	}
}
