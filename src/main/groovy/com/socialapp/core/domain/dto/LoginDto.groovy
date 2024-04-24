package com.socialapp.core.domain.dto

import jakarta.validation.constraints.Email

class LoginDto {

	@Email
	String email
	String password
}
