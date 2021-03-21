package com.cognizant.authorization.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Pod3
 * @version 1.8
 * @apiNote This class is used for handling exception. When the user provides
 *          wrong credentials then this exception will be thrown. Here we are
 *          extending {@link RuntimeException} which is an unchecked exception
 *
 */
@Slf4j
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * A no-arg constructor
	 */
	public UserNotFoundException() {
		super();
		log.info("Inside UserNotFoundException no args constructor");
	}

	/**
	 * A constructor which will take message as parameter
	 * 
	 * @param message
	 */
	public UserNotFoundException(String message) {
		super(message);
		log.debug("message{}:", message);
	}

}
