package com.socialapp.core.service.exception

class BadCredentialsException extends RuntimeException{
	BadCredentialsException(final String message) {
		super(message)
	}
}
