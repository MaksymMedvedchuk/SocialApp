package com.socialapp.core.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty

class CommentDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	String id
	String comment
}
