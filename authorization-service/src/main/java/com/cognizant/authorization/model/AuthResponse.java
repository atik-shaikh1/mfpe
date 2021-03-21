package com.cognizant.authorization.model;

import com.cognizant.authorization.controller.AuthController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Model class whose fields are used for the response for token validation. When
 * we call the method getValidity() in {@link AuthController} then it will
 * return {@link AuthResponse} type object
 * 
 * @author Pod3
 */
@Data
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
