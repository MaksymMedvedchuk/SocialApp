package com.socialapp.controller.exceptionhandler

import com.mongodb.MongoWriteException
import com.socialapp.core.service.exception.BadCredentialsException
import com.socialapp.core.service.exception.DuplicateEmailException
import com.socialapp.core.service.exception.PasswordEmptyOrNullException
import com.socialapp.core.service.exception.ResourceNotfoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import java.util.stream.Collectors

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DuplicateEmailException.class)
	ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage())
	}

	@ExceptionHandler(ResourceNotfoundException.class)
	ResponseEntity<String> handleResourceNotfoundException(ResourceNotfoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage())
	}

	@ExceptionHandler(MongoWriteException.class)
	ResponseEntity<String> handleMongoWriteException(MongoWriteException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getError().getMessage())
	}

	@ExceptionHandler(PasswordEmptyOrNullException.class)
	ResponseEntity<String> handlePasswordEmptyOrNullException(PasswordEmptyOrNullException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage())
	}

	@ExceptionHandler(BadCredentialsException.class)
	ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage())
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			final MethodArgumentNotValidException ex,
			final HttpHeaders headers,
			final HttpStatusCode status,
			final WebRequest request
	) {
		BindingResult bindingResult = ex.getBindingResult();
		List<String> errors = bindingResult.getFieldErrors()
				.stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.collect(Collectors.toList())
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST)
	}
}
