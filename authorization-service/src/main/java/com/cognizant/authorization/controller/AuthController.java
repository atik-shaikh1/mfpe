package com.cognizant.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authorization.model.AuthResponse;
import com.cognizant.authorization.model.UserLoginCredential;
import com.cognizant.authorization.model.UserToken;
import com.cognizant.authorization.repository.UserDAO;
import com.cognizant.authorization.service.CustomerDetailsService;
import com.cognizant.authorization.service.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is having all the endpoints related to authorization purpose. For
 * getting the token and validating the token this class willbe used.
 * 
 * @version 1.8
 * @author Pod3
 */
@Slf4j
@RestController
public class AuthController {

	/**
	 * This is a private field of type {@link JwtUtil} class which provides the
	 * utilities for the token like get token, validate token, expiration time etc.
	 */
	@Autowired
	private JwtUtil jwtutil;

	/**
	 * This is a private field of type {@link CustomerDetailsService} class which is
	 * used to fetch the user credentials from the database
	 */
	@Autowired
	private CustomerDetailsService custdetailservice;

	/**
	 * Thisis a private field of type {@link UserDAO} class which
	 * {@link ExtendedAddShiftOp} JpaRepository interface
	 */
	@Autowired
	private UserDAO userservice;

	/**
	 * This is a private field of type {@link AuthenticationManager} class
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * This method is checking if the userid and password provided by the user are
	 * correct or not. For that it will call authenticate method of class
	 * {@link AuthenticationManager} and pass in the parameter userid and password.
	 * If the userid and password given not present in the database then a Exception
	 * which may be {@link DisabledException} or {@link BadCredentialsException}
	 * will be thrown that we will catch in catch block.
	 * 
	 * @param userid
	 * @param password
	 * @throws Exception
	 */
	private void authenticate(String userid, String password) throws Exception {
		log.info("Start");
		log.debug("userid and password{}:", userid, password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userid, password));
		} catch (DisabledException e) {
			log.error("Not a valid user");
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			log.error("Not a valid user");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	/**
	 * This method is used to check the credentials whether the provided credentials
	 * are correct or not. For this we will call authenticate method. If user
	 * credentials are correct then we will fetch the data from database based on
	 * userid and return it to the user with a token
	 * 
	 * @param userlogincredentials An object of type {@link UserLoginCredential}
	 *                             which has two fields userid and password
	 * @return an object of type UserToken which has two fields userid and authtoken
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserToken> login(@RequestBody UserLoginCredential userlogincredentials) throws Exception {
		log.info("Start");
		authenticate(userlogincredentials.getUserid(), userlogincredentials.getPassword());
		final UserDetails userdetails = custdetailservice.loadUserByUsername(userlogincredentials.getUserid());
		log.debug("userdetails{}:", userdetails);
		return new ResponseEntity<>(new UserToken(userlogincredentials.getUserid(), jwtutil.generateToken(userdetails)),
				HttpStatus.OK);

	}

	/**
	 * This method is used to validate the token. This method will take the
	 * parameter token from the authorization header and that token will be having
	 * starting letters as Bearer and then one space which we will remove and send
	 * to the {@link JwtUtil} class's method validateToken, if the response comes as
	 * true we will set the values on {@link AuthResponse} type object's field
	 * isValid as true and return it to the user and if the response comes as false
	 * then we will set the isValid field of {@link AuthResponse} as false
	 * 
	 * @param token from the authorization header
	 * @return a string message if validation fails otherwise it will return an
	 *         object authResponse of type {@link AuthResponse} which has two fields
	 *         userid, username and isValid
	 * @throws TokenValidationFailedException
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	public ResponseEntity<?> getValidity(@RequestHeader("Authorization") final String token) {
		log.debug("token{}:", token);
		String token1 = token.substring(7);
		log.debug("token after removing bearer{}:", token1);
		AuthResponse authResponse = new AuthResponse();
		if (jwtutil.validateToken(token1)) {
			log.info("token is valid");
			authResponse.setUserid(jwtutil.extractUsername(token1));
			authResponse.setValid(true);
			authResponse
					.setUsername((userservice.findById(jwtutil.extractUsername(token1)).orElse(null).getUsername()));
			log.debug("authResponse{}:", authResponse);
		} else {
			log.error("Token validation failed");
			authResponse.setValid(false);
		}
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

}
