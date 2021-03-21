package com.cognizant.medicinestock.exception;

/**
 * @author Pod3
 * @version 1.8
 * @apiNote Whenever the treating ailment not present then we will throw this
 *          exception and it throws RuntimeException.
 * 
 * @see RuntimeException
 *
 */
public class TreatingAilmentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * No-arg constructor of {@link TreatingAilmentNotFoundException}
	 */
	public TreatingAilmentNotFoundException() {
		super();
	}

	/**
	 * No-arg constructor of {@link TreatingAilmentNotFoundException}
	 */
	public TreatingAilmentNotFoundException(String message) {
		super(message);
	}

}
