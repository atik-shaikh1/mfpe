package com.cognizant.medicinestock.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.medicinestock.model.AuthResponse;


/**
 * @author Pod3
 * @version 1.8
 * @apiNote This interface communicates with authorization service to verify the
 *          token. It takes the url of the service to whom we wants to
 *          communicate and the name attribute in annotation FeignClient must be
 *          the name we have specified in the properties file of the service to
 *          whom we wants to communicate.
 * @see AuthResponse
 *
 */
@FeignClient(name = "authorization-service", url = "http://localhost:8084")
public interface AuthFeignClient {

	/**
	 * This method will verify token whether it is valid or not. It is expired or
	 * not.
	 * 
	 * @param token
	 * @return An object of type AuthResponse which has fields userid, username and
	 *         isValid.
	 * 
	 * @see AuthResponse
	 */
	@GetMapping(path = "/api/auth/validate")
	public AuthResponse verifyToken(@RequestHeader(name = "Authorization", required = true) String token);

}
