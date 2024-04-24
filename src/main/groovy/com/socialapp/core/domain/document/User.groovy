package com.socialapp.core.domain.document


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
class User {

	@Id
	private String id
	@Indexed(unique = true)
	private String userName
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
