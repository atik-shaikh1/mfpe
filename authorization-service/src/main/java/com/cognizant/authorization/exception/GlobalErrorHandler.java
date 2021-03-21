package com.cognizant.authorization.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cognizant.authorization.model.CustomErrorResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to handle all the errors. Here we are using class
 * {@link CustomErrorResponse} for returning the response. It will handle
 * generic as well as specific exceptions also.
 * 
 * @author Pod3
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

	/**
	 * This method will handle all type of exceptions
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomErrorResponse> handleAllMedicineStockErrors(Exception ex) {
		log.info("Start");
		CustomErrorResponse response = new CustomErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.BAD_REQUEST);
		response.setReason("Bad request");
		log.debug("custom error response{}:", response);
		return new ResponseEntity<CustomErrorResponse>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * This method will handle the exception when the provided user credentials wont
	 * match the values residing in the database
	 * 
	 * @param ex
	 * @return
	 */
	/*
	 * @ExceptionHandler(UserNotFoundException.class) public
	 * ResponseEntity<CustomErrorResponse>
	 * handleUserNotFoundErrors(UserNotFoundException ex) { log.info("Start");
	 * CustomErrorResponse response = new CustomErrorResponse();
	 * response.setTimestamp(LocalDateTime.now());
	 * response.setMessage(ex.getMessage());
	 * response.setStatus(HttpStatus.FORBIDDEN);
	 * response.setReason("You might have entered wrong credentials");
	 * log.debug("custom error response{}:", response); return new
	 * ResponseEntity<CustomErrorResponse>(response, HttpStatus.FORBIDDEN); }
	 */

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<CustomErrorResponse> handleInternalAuthenticationServiceException(
			InternalAuthenticationServiceException ex) {
		log.info("Start");
		CustomErrorResponse response = new CustomErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.FORBIDDEN);
		response.setReason("You might have entered wrong credentials");
		log.debug("custom error response{}:", response);
		return new ResponseEntity<CustomErrorResponse>(response, HttpStatus.FORBIDDEN);
	}

	/**
	 * This method will be called if the Token validation will fail.
	 * 
	 * @param ex
	 * @return
	 *//*
		 * @ExceptionHandler(TokenValidationFailedException.class) public
		 * ResponseEntity<CustomErrorResponse>
		 * handleTokenValidationFailedErrors(TokenValidationFailedException ex) {
		 * log.info("Start"); CustomErrorResponse response = new CustomErrorResponse();
		 * response.setTimestamp(LocalDateTime.now());
		 * response.setMessage(ex.getMessage());
		 * response.setStatus(HttpStatus.FORBIDDEN);
		 * response.setReason("You might have entered wrong credentials"); return new
		 * ResponseEntity<CustomErrorResponse>(response, HttpStatus.NOT_FOUND); }
		 */

}
