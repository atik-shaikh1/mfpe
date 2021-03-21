package com.cognizant.authorization.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to intercept every method. It extends class
 * {@link OncePerRequestFilter} that aims to guarantee a single execution per
 * request dispatch, on any servlet container. It provides a
 * doFilterInternalmethod with HttpServletRequest and HttpServletResponse
 * arguments.
 *
 * @version 1.8
 * @author Pod3
 * @see HttpServletRequest
 * @see HttpServletResponse
 */
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	/**
	 * This holds the object of type {@link JwtUtil} class which will be injected
	 * automatically because of the annotation autowired.
	 */
	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * This holds the object of type {@link CustomerDetailsService} class which will
	 * be injected automatically because of the annotation autowired.
	 */
	@Autowired
	private CustomerDetailsService custDetailsService;

	/**
	 * This method guaranteed to be just invoked once per request within a single
	 * request thread.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.debug("request + response + filterchain{}:", request, response, filterChain);
		final String authHeadder = request.getHeader("Authorization");
		log.debug("authHeader{}:", authHeadder);
		String username = null;
		String jwt = null;

		if (authHeadder != null && authHeadder.startsWith("Bearer ")) {
			jwt = authHeadder.substring(7);
			log.debug("jwt token{}:", jwt);
			username = jwtUtil.extractUsername(jwt);
			log.debug("username{}:", username);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			log.info("Username is not null");
			UserDetails userDetails = this.custDetailsService.loadUserByUsername(username);
			log.debug("userDetails{}:", userDetails);
			if (jwtUtil.validateToken(jwt, userDetails)) {
				log.info("token is valid");
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				log.debug("usernamePasswordAuthenticationToken{}:", usernamePasswordAuthenticationToken);
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
