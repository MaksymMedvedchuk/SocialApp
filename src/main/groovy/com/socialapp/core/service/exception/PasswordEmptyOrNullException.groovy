package com.socialapp.core.service.exception

class PasswordEmptyOrNullException extends RuntimeException{
	PasswordEmptyOrNullException(final String message) {
		super(message)
	}
}
