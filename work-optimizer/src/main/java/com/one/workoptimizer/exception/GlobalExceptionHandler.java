package com.one.workoptimizer.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
/**
 * 
 * @author Ashish G
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * @param ex
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ErrorResponse> handleForInvalidInput(InvalidInputException ex) {

		ErrorResponse errors = new ErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
