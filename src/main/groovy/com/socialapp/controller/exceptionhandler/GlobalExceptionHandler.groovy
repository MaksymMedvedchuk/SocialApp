package com.socialapp.controller.exceptionhandler

import com.mongodb.MongoWriteException
import com.socialapp.core.service.exception.DuplicateEmailException
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
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(ResourceNotfoundException.class)
	ResponseEntity<String> handleDuplicateEmailException(ResourceNotfoundException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(MongoWriteException.class)
	ResponseEntity<String> handleDuplicateEmailException(MongoWriteException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getError().getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			final MethodArgumentNotValidException ex,
			final HttpHeaders headers,
			final HttpStatusCode status,
			final WebRequest request
	) {
		BindingResult bindingResult = ex.getBindingResult();
		List<String> errorMessages = bindingResult.getFieldErrors()
				.stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.collect(Collectors.toList())

		return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
	}
}
