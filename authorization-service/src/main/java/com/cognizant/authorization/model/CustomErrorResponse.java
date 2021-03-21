package com.cognizant.authorization.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.cognizant.authorization.exception.GlobalErrorHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class we are using as a response of error handling message. In the
 * {@link GlobalErrorHandler} class we are using this
 * {@link CustomErrorResponse} class as a return type that will be shown to the
 * client whenever any kind of exception occurs. The fields of this class will
 * be used to show this kind of response.
 * 
 * @version 1.8
 * @author Pod3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomErrorResponse {

	/**
	 * This field will show the time when the exception occurs
	 */
	private LocalDateTime timestamp;

	/**
	 * This field will show the status which we will be setting in this field from
	 * {@link GlobalErrorHandler} class.
	 */
	private HttpStatus status;

	/**
	 * This field will represent the reason why the exception has been occured. This
	 * we will set in the {@link GlobalErrorHandler} class
	 */
	private String reason;

	/**
	 * This field will represent the message which we will set in the constructor of
	 * exception class. This we will set in the {@link GlobalErrorHandler} class
	 */
	private String message;

}