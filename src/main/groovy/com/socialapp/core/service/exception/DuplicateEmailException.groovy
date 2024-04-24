package com.socialapp.core.service.exception

class DuplicateEmailException extends RuntimeException {
	DuplicateEmailException(final String message) {
		super(message)
	}
}
