package com.socialapp.core.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.socialapp.core.domain.document.Post
import groovy.transform.builder.Builder
import jakarta.validation.constraints.Email

@Builder
class UserDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	String id
	String userName
	@Email
	String email
	String password
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	boolean isRegister
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	boolean isLogin
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	List<Post> posts
}
