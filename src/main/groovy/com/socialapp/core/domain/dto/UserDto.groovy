package com.socialapp.core.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import jakarta.validation.constraints.Email
import org.springframework.data.mongodb.core.index.Indexed

@Canonical
class UserDto {

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String id
	private String userName
	@Email
	private String email
	private String password

	String getId() {
		return id
	}

	void setId(final String id) {
		this.id = id
	}

	String getUserName() {
		return userName
	}

	void setUserName(final String userName) {
		this.userName = userName
	}

	String getEmail() {
		return email
	}

	void setEmail(final String email) {
		this.email = email
	}

	String getPassword() {
		return password
	}

	void setPassword(final String password) {
		this.password = password
	}
}
