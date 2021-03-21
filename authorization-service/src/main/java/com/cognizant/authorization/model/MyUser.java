package com.cognizant.authorization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class is an entity which we will be storing in the database. We will
 * store the values already in the database for checking the user login
 * credentials and this entity would help us to do that.
 * 
 * @version 1.8
 * @author Pod3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "user")
public class MyUser {

	/**
	 * This will be the id for the user credentials that user will have to enter in
	 * the request body
	 */
	@Id
	@Column(name = "userid", length = 20)
	private String userid;

	/**
	 * This field will be used to store the password that user will have to enter in
	 * the request body
	 */
	@Column(name = "password", length = 20)
	private String password;

	/**
	 * This field will be used to store the username that user will have to enter in
	 * the request body
	 */
	@Column(name = "username", length = 20)
	private String username;

}
