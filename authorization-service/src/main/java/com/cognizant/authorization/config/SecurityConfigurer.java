package com.cognizant.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cognizant.authorization.service.CustomerDetailsService;
import com.cognizant.authorization.service.JwtRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used for the security configuration. It extends the class
 * WebSecurityConfigurerAdapter It will take care of the authentication and
 * authorization based on the user credentials.
 * 
 * @version 1.8
 * @author Pod3
 */
@Slf4j
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	/**
	 * This is a private field of class {@link CustomerDetailsService} class is used
	 * to load the user credentials from the database. Based on that database
	 * fetched user credentials authentication will be performed
	 */
	@Autowired
	private CustomerDetailsService emsuserDetailsService;

	/**
	 * This is a private field of class {@link JwtRequestFilter} This class extends
	 * {@link OncePerRequestFilter} so every request will first hit this filter
	 */
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	/**
	 * This methods used to override the credentials which spring automatically
	 * generates By using {@link AuthenticationManagerBuilder} object we are
	 * overriding the security credentials with our own given credentials It will
	 * call the userDetailsService method on the
	 * {@link AuthenticationManagerBuilder} class object and this method is present
	 * in class {@link AuthenticationManagerBuilder}
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Start");
		super.configure(auth);
		auth.userDetailsService(emsuserDetailsService);

	}

	/**
	 * This method is used for the configuration of authorization part. Here we are
	 * disabling cross-site request forgery which is an attack that forces
	 * authenticated users to submit a request to a Web application against which
	 * they are currently authenticated. And we are permitting the request for all
	 * which starts with /login and all other requests will be authenticated. Here
	 * we are using stateless protocol because we do not want to add data in form of
	 * cookies or in any state
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Start");
		SessionManagementConfigurer<HttpSecurity> sessionCreationPolicy = http.csrf().disable().authorizeRequests()
				.antMatchers("/login").permitAll().anyRequest().authenticated().and().exceptionHandling().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		log.debug("sessionCreatePolicy{}:", sessionCreationPolicy);
		HttpSecurity addFilterBefore = http.addFilterBefore(jwtRequestFilter,
				UsernamePasswordAuthenticationFilter.class);
		log.debug("addFilterBefore{}:", addFilterBefore);
	}

	/**
	 * This method is used to inject a {@link AuthenticationManager} type bean We
	 * are annotating this method with @Bean. @Bean annotation tells that a method
	 * produces a bean to be managed by the Spring container.
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.info("Start");
		return super.authenticationManagerBean();
	}

	/**
	 * Password encoder is an interface which is used through the authorization
	 * process. The encode function shall be used to encode your password and the
	 * matches function will check if your raw password matches the encoded
	 * password.
	 */
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		log.info("Start");
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * We are authorizing the requests over here. The url we are giving to the
	 * method antMatchers(), these urls should not be put behind the authentication
	 * wall. WebSecurity allows adding {@link RequestMatcher} instances that Spring
	 * Security should ignore.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info("Start");
		IgnoredRequestConfigurer antMatchers = web.ignoring().antMatchers("/login", "/h2-console/**", "/v2/api-docs",
				"/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html",
				"/webjars/**", "/manage/health/**");
		log.debug("antMatchers{}:", antMatchers);
	}

}
