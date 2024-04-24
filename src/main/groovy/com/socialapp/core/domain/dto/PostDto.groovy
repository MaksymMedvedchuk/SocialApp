package com.socialapp.core.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

class PostDto {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	String id
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	String userId
	@NotBlank
	String post
}

