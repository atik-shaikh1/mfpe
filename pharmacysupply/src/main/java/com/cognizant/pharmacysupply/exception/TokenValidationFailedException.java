package com.cognizant.pharmacysupply.exception;

/**
 * Whenever the token validation fails then we will throw this exception and it
 * extends RuntimeException.
 * 
 * @version 1.8
 * @author Pod3
 * @see RuntimeException
 */
public class TokenValidationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * All-arg constructor of {@link TokenValidationFailedException}
	 */
	public TokenValidationFailedException(String message) {
		super(message);
	}

}
