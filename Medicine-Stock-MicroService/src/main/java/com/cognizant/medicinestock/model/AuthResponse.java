package com.cognizant.medicinestock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Pod3
 * @version 1.8
 * @apiNote Model class whose fields are used for the response for token
 *         validation. When we call the method getValidity() in
 *         {@link AuthController} then it will return {@link AuthResponse} type
 *         object
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponse {
	/**
	 * This is a private field which is used to represent the userid for user login
	 * credentials. The data type is String.
	 */
	private String userid;

	/**
	 * This is a private field which is used to represent the username for user
	 * login credentials. The data type is String.
	 */
	private String username;

	/**
	 * This is a private field which is used to represent whethet the token is valid
	 * or not. The data type is boolean.
	 */
	private boolean isValid;
}
