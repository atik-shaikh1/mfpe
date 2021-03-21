package com.cognizant.authorization.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provides several utility methods in context to jwt token.
 * 
 * @author Pod3
 * @version 1.8
 */
@Slf4j
@Service
public class JwtUtil {

	/**
	 * This field contains a secret key which will be used to create a token
	 */
	private String secretkey = "sampletest";

	/**
	 * This method is used to extract the username from the token
	 * 
	 * @param token in the string format
	 * @return
	 */
	public String extractUsername(String token) {
		log.debug("token{}:", token);
		String extractClaim = extractClaim(token, Claims::getSubject);
		log.debug("extractClaim{}:", extractClaim);
		return extractClaim;
	}

	/**
	 * This method is used to extract the expiration time of the jwt token
	 * 
	 * @param token
	 * @return
	 */
	public Date extractExpiration(String token) {
		log.debug("token{}:", token);
		Date expiryDate = extractClaim(token, Claims::getExpiration);
		log.debug("expiryDate{}:", expiryDate);
		return expiryDate;

	}

	/**
	 * This method is used to extract a particular claim for the token
	 * 
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		log.debug("token{}:", token);
		log.debug("claimsResolver{}:", claimsResolver);
		final Claims claims = extractAllClaims(token);
		log.debug("claims{}:", claims);
		T apply = claimsResolver.apply(claims);
		log.debug("apply{}:", apply);
		return apply;
	}

	/**
	 * This method is used to extract claims for the token
	 * 
	 * @param token
	 * @return
	 */
	private Claims extractAllClaims(String token) {
		log.debug("token{}:", token);
		Claims claims = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
		log.debug("claims{}:", claims);
		return claims;
	}

	/**
	 * Will tell whether the token is expired or not.
	 * 
	 * @param token
	 * @return type is boolean. If token is expired, method will return true o/w
	 *         return false
	 */
	private Boolean isTokenExpired(String token) {
		log.debug("token{}:", token);
		boolean isTokenExpired = extractExpiration(token).before(new Date());
		log.debug("isTokenExpired{}:", isTokenExpired);
		return isTokenExpired;
	}

	/**
	 * This method will generate token based on the given parameter userDetails
	 * 
	 * @param userDetails
	 * @return a String type token
	 */
	public String generateToken(UserDetails userDetails) {
		log.debug("userDetails{}:", userDetails);
		Map<String, Object> claims = new HashMap<>();
		log.debug("claims{}:", claims);
		String createToken = createToken(claims, userDetails.getUsername());
		log.debug("createToken{}:", createToken);
		return createToken;
	}

	/**
	 * This method is used to create token based on the claims and subject given as
	 * parameter. It will add a signature to the jwt token based on the algorithm
	 * HS256.
	 * 
	 * @param claims
	 * @param subject
	 * @return
	 */
	private String createToken(Map<String, Object> claims, String subject) {
		log.debug("claims{}:", claims);
		log.debug("subject{}:", subject);
		String token = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))// token for 30 mins
				.signWith(SignatureAlgorithm.HS256, secretkey).compact();
		log.debug("token{}:", token);
		return token;
	}

	/**
	 * This method is used to validate token based on the given token and
	 * userDetails as parameter. First from the token we will extract the username
	 * and then will check in the database whether the token extracted username and
	 * the user residing in database is same or not and also will check whether the
	 * token has been expired or not
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		log.debug("token{}:", token);
		log.debug("userDetails{}:", userDetails);
		final String username = extractUsername(token);
		log.debug("userName{}:", username);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * 
	 * This method is used to vaidate the token based on the give parameter as token
	 * and it will check whether the token is expired or not by calling a method
	 * isTokenExpired()
	 * 
	 * @param token
	 * @return
	 */
	public Boolean validateToken(String token) {
		log.debug("token{}:", token);
		return !isTokenExpired(token);
	}
}