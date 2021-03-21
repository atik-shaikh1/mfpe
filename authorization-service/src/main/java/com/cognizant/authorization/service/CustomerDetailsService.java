package com.cognizant.authorization.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.authorization.exception.UserNotFoundException;
import com.cognizant.authorization.model.MyUser;
import com.cognizant.authorization.repository.UserDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to load the user details from database and checking
 * whether the user resides in database or not based on the given id. This class
 * will implement the {@link UserDetailsService} interface. The interface
 * requires only one read-only method, which simplifies support for
 * newdata-access strategies.
 *
 * @author Pod3
 * @version 1.8
 */
@Service
@Slf4j
public class CustomerDetailsService implements UserDetailsService {

	/**
	 * This field is used to communicate with the database. It has the annotation
	 * autowired which will automatically inject the bean.
	 * 
	 * @see UserDAO
	 */
	@Autowired
	private UserDAO userdao;

	/**
	 * This method is used to load the user details from database and checking
	 * whether the user resides in database or not based on the given id. If the
	 * user not present in the database it will throw an exception
	 * UsernameNotFoundException. And if user is present in the database it will
	 * simply return the {@link User} object
	 * 
	 * @exception UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		log.debug("uid ", uid);
		MyUser custuser = userdao.findById(uid).orElseThrow(() -> new UserNotFoundException(
				"You are not an authenticated user. Please try to login with the valid credentials"));
		log.debug("custuser{}:", custuser);
		return new User(custuser.getUserid(), custuser.getPassword(), new ArrayList<>());

	}

}
